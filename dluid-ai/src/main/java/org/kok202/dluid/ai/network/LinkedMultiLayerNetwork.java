package org.kok202.dluid.ai.network;

import lombok.Data;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

import java.util.ArrayList;
import java.util.List;

@Data
public class LinkedMultiLayerNetwork {
    private long inputLayerId;
    private boolean isTestModel;
    private List<MultiLayerNetwork> multiLayerNetworks;

    public LinkedMultiLayerNetwork() {
        multiLayerNetworks = new ArrayList<>();
    }

    public LinkedMultiLayerNetwork deepCopy(){
        LinkedMultiLayerNetwork linkedMultiLayerNetwork = new LinkedMultiLayerNetwork();
        linkedMultiLayerNetwork.inputLayerId = inputLayerId;
        linkedMultiLayerNetwork.multiLayerNetworks = new ArrayList<>(multiLayerNetworks);
        return linkedMultiLayerNetwork;
    }
}
