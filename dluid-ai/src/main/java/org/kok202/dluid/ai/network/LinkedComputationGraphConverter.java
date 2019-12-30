package org.kok202.dluid.ai.network;

import org.deeplearning4j.nn.graph.ComputationGraph;

import java.util.List;
import java.util.stream.Collectors;

public class LinkedComputationGraphConverter {
    public List<Model> convert(List<LinkedComputationGraph> linkedComputationGraphs){
        return linkedComputationGraphs.stream()
                .map(this::initializeModel)
                .collect(Collectors.toList());
    }

    private Model initializeModel(LinkedComputationGraph linkedComputationGraph){
        linkedComputationGraph.getComputationGraphs().forEach(ComputationGraph::init);

        // TODO : 연결하고 동시에 학습이 가능한 방법을 찾아야만 한다...
//        List<Layer> allComputationGraphConfigurationLayers = new ArrayList<>();
//        for (ComputationGraph computationGraph : linkedComputationGraph.getComputationGraphs()) {
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
//        ComputationGraph totalComputationGraph = new ComputationGraph(totalComputationGraphConfiguration);
//        totalComputationGraph.init();

        return Model.builder()
                .isTestModel(linkedComputationGraph.isTestModel())
                .inputLayerId(linkedComputationGraph.getInputLayerId())
                .totalComputationGraph(null)
                .computationGraphs(linkedComputationGraph.getComputationGraphs())
                .build();
    }
}