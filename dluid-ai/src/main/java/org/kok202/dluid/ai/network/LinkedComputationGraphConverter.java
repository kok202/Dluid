package org.kok202.dluid.ai.network;

import org.deeplearning4j.nn.conf.ComputationGraphConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.Layer;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.kok202.dluid.ai.singleton.AISingleton;
import org.kok202.dluid.domain.util.RandomUtil;

import java.util.ArrayList;
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

        List<Layer> allComputationGraphConfigurationLayers = new ArrayList<>();
        for (ComputationGraph computationGraph : linkedComputationGraph.getComputationGraphs()) {
            computationGraph.init();
            computationGraph.getVertices()
            for (org.deeplearning4j.nn.api.Layer layer : computationGraph.getLayers()) {
                allComputationGraphConfigurationLayers.add(layer.conf().getLayer());
            }
        }

        ComputationGraphConfiguration totalComputationGraphConfiguration = new NeuralNetConfiguration.Builder()
                .seed(RandomUtil.getLong())
                .updater(AISingleton.getInstance().getModelManager().getModelParameter().getIUpdater())
                .weightInit(AISingleton.getInstance().getModelManager().getModelParameter().getWeightInit())
                .graphBuilder()
                .()
                .list(allComputationGraphConfigurationLayers.toArray(new Layer[0]))

                .build();

        ComputationGraph totalComputationGraph = new ComputationGraph(totalComputationGraphConfiguration);
        totalComputationGraph.init();

        return Model.builder()
                .isTestModel(linkedComputationGraph.isTestModel())
                .inputLayerId(linkedComputationGraph.getInputLayerId())
                .totalComputationGraph(totalComputationGraph)
                .computationGraphs(linkedComputationGraph.getComputationGraphs())
                .build();
    }
}