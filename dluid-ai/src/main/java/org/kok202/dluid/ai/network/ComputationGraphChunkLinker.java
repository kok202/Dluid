package org.kok202.dluid.ai.network;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ComputationGraphChunkLinker {
    public List<LinkedComputationGraph> link(ComputationGraphChunkSet computationGraphChunkSet){
        List<LinkedComputationGraph> linkedComputationGraphs = new ArrayList<>();

        for(long from : computationGraphChunkSet.getLinkageFromTo().get(computationGraphChunkSet.getOutputLayerId())){
            LinkedComputationGraph linkedComputationGraphStack = new LinkedComputationGraph();
            linkedComputationGraphStack.getComputationGraphs().add(computationGraphChunkSet.getManagersMap().get(computationGraphChunkSet.getOutputLayerId()));
            linkedComputationGraphStack.getComputationGraphs().add(computationGraphChunkSet.getManagersMap().get(from));
            findAllCombinationSearch(computationGraphChunkSet, linkedComputationGraphs, linkedComputationGraphStack, from);
        }

        return linkedComputationGraphs;
    }

    private void findAllCombinationSearch(ComputationGraphChunkSet computationGraphChunkSet, List<LinkedComputationGraph> linkedComputationGraphs, LinkedComputationGraph linkedComputationGraphStack, long current){
        List<Long> froms = computationGraphChunkSet.getLinkageFromTo().get(current);
        if(froms == null || froms.isEmpty()){
            // Find one combination!
            LinkedComputationGraph linkedComputationGraphInstance = linkedComputationGraphStack.deepCopy();
            linkedComputationGraphInstance.setTestModel(current == computationGraphChunkSet.getTestInputLayerId());
            linkedComputationGraphInstance.setInputLayerId(current);
            linkedComputationGraphs.add(linkedComputationGraphInstance);
            return;
        }

        for (Long from : froms) {
            linkedComputationGraphStack.getComputationGraphs().add(computationGraphChunkSet.getManagersMap().get(from));
            findAllCombinationSearch(computationGraphChunkSet, linkedComputationGraphs, linkedComputationGraphStack, from);
            linkedComputationGraphStack.getComputationGraphs().remove(computationGraphChunkSet.getManagersMap().get(from));
        }
    }
}
