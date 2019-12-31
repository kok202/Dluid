package org.kok202.dluid.ai.network;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MultiLayerNetworkChunkLinker {
    public List<LinkedMultiLayerNetwork> link(MultiLayerNetworkChunkSet multiLayerNetworkChunkSet){
        List<LinkedMultiLayerNetwork> linkedMultiLayerNetworks = new ArrayList<>();

        // It means there is no switch layer.
        if(multiLayerNetworkChunkSet.getLinkageFromTo().get(multiLayerNetworkChunkSet.getOutputLayerId()) == null){
            LinkedMultiLayerNetwork linkedMultiLayerNetwork = new LinkedMultiLayerNetwork();
            linkedMultiLayerNetwork.setInputLayerId(multiLayerNetworkChunkSet.getOutputLayerId());
            linkedMultiLayerNetworks.add(linkedMultiLayerNetwork);
            return linkedMultiLayerNetworks;
        }

        for(long from : multiLayerNetworkChunkSet.getLinkageFromTo().get(multiLayerNetworkChunkSet.getOutputLayerId())){
            LinkedMultiLayerNetwork linkedMultiLayerNetworkStack = new LinkedMultiLayerNetwork();
            linkedMultiLayerNetworkStack.getMultiLayerNetworks().add(multiLayerNetworkChunkSet.getManagersMap().get(multiLayerNetworkChunkSet.getOutputLayerId()));
            linkedMultiLayerNetworkStack.getMultiLayerNetworks().add(multiLayerNetworkChunkSet.getManagersMap().get(from));
            findAllCombinationSearch(multiLayerNetworkChunkSet, linkedMultiLayerNetworks, linkedMultiLayerNetworkStack, from);
        }
        return linkedMultiLayerNetworks;
    }

    private void findAllCombinationSearch(MultiLayerNetworkChunkSet multiLayerNetworkChunkSet, List<LinkedMultiLayerNetwork> linkedMultiLayerNetworks, LinkedMultiLayerNetwork linkedMultiLayerNetworkStack, long current){
        List<Long> froms = multiLayerNetworkChunkSet.getLinkageFromTo().get(current);
        if(froms == null || froms.isEmpty()){
            // Find one combination!
            LinkedMultiLayerNetwork linkedMultiLayerNetworkInstance = linkedMultiLayerNetworkStack.deepCopy();
            linkedMultiLayerNetworkInstance.setInputLayerId(current);
            linkedMultiLayerNetworks.add(linkedMultiLayerNetworkInstance);
            return;
        }

        for (Long from : froms) {
            linkedMultiLayerNetworkStack.getMultiLayerNetworks().add(multiLayerNetworkChunkSet.getManagersMap().get(from));
            findAllCombinationSearch(multiLayerNetworkChunkSet, linkedMultiLayerNetworks, linkedMultiLayerNetworkStack, from);
            linkedMultiLayerNetworkStack.getMultiLayerNetworks().remove(multiLayerNetworkChunkSet.getManagersMap().get(from));
        }
    }
}
