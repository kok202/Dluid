package org.kok202.dluid.ai.network;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

import java.util.List;
import java.util.stream.Collectors;

public class LinkedMultiLayerNetworkConverter {
    public List<Model> convert(List<LinkedMultiLayerNetwork> linkedMultiLayerNetworks){
        return linkedMultiLayerNetworks.stream()
                .map(this::initializeModel)
                .collect(Collectors.toList());
    }

    private Model initializeModel(LinkedMultiLayerNetwork linkedMultiLayerNetwork){
        linkedMultiLayerNetwork.getMultiLayerNetworks().forEach(MultiLayerNetwork::init);

        // TODO : 연결하고 동시에 학습이 가능한 방법을 찾아야만 한다...
//        List<Layer> allComputationGraphConfigurationLayers = new ArrayList<>();
//        for (ComputationGraph computationGraph : linkedMultiLayerNetwork.getMultiLayerNetworks()) {
//            computationGraph.init();
//            computationGraph.getVertices()
//            for (org.deeplearning4j.nn.api.Layer layer : computationGraph.getLayers()) {
//                allComputationGraphConfigurationLayers.add(layer.conf().getLayer());
//            }
//        }
//
//        ComputationGraphConfiguration totalComputationGraphConfiguration = new NeuralNetConfiguration.Builder()
//                .seed(RandomUtil.getLong())
//                .updater(AISingleton.getInstance().getModelManager().getModelParameter().getIUpdater())
//                .weightInit(AISingleton.getInstance().getModelManager().getModelParameter().getWeightInit())
//                .graphBuilder()
//                .()
//                .list(allComputationGraphConfigurationLayers.toArray(new Layer[0]))
//
//                .build();
//
//        ComputationGraph totalMultiLayerNetwork = new ComputationGraph(totalComputationGraphConfiguration);
//        totalMultiLayerNetwork.init();

        return Model.builder()
                .inputLayerId(linkedMultiLayerNetwork.getInputLayerId())
                .totalMultiLayerNetwork(null)
                .multiLayerNetworks(linkedMultiLayerNetwork.getMultiLayerNetworks())
                .build();
    }
}