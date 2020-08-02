package org.kok202.dluid.ai.network;

import org.deeplearning4j.nn.api.Layer;
import org.deeplearning4j.nn.conf.ComputationGraphConfiguration;
import org.deeplearning4j.nn.conf.InputPreProcessor;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.distribution.UniformDistribution;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.weights.WeightInit;
import org.kok202.dluid.ai.network.layer.LayerBindManager;
import org.kok202.dluid.ai.singleton.AISingleton;
import org.kok202.dluid.domain.entity.enumerator.BiasInitializer;
import org.kok202.dluid.domain.entity.enumerator.WeightInitializer;

import java.util.*;
import java.util.stream.Collectors;

public class ModelConverter {
    private Map<String, InputPreProcessor> inputPreProcessorRecycleMap;

    public ModelConverter() {
        this.inputPreProcessorRecycleMap = new HashMap<>();
    }

    public List<Model> convert(LayerGraphManagerAnalyzer layerGraphManagerAnalyzer){
        return layerGraphManagerAnalyzer.getInputLayerIds()
                .stream()
                .map(inputLayerId -> {
                    List<org.kok202.dluid.domain.entity.Layer> dluidLayers = new ArrayList<>(layerGraphManagerAnalyzer.getMultiLayerManager().get(inputLayerId).values());

                    ComputationGraphConfiguration.GraphBuilder graphBuilder = createDefaultConfiguration();
                    dluidLayers.forEach(dluidLayer -> LayerBindManager.bind(dluidLayer, layerGraphManagerAnalyzer.getLayerFroms(dluidLayer, inputLayerId), graphBuilder));
                    recycleInputPreProcessor(graphBuilder, dluidLayers);

                    ComputationGraphConfiguration computationGraphConfiguration = graphBuilder.build();
                    ComputationGraph computationGraph = new ComputationGraph(computationGraphConfiguration);
                    computationGraph.init();

                    Map<String, Layer> computationGraphLayerMap = new LinkedHashMap<>();
                    for (org.kok202.dluid.domain.entity.Layer dluidLayer : dluidLayers) {
                        String dluidLayerId = dluidLayer.getId();
                        if(computationGraph.getVertex(dluidLayerId).hasLayer()){
                            Layer computationGraphLayer = computationGraph.getVertex(dluidLayerId).getLayer();
                            computationGraphLayerMap.put(dluidLayerId, computationGraphLayer);
                            inputPreProcessorRecycleMap.put(dluidLayerId, graphBuilder.getInputPreProcessors().get(dluidLayerId));
                        }
                    }

                    return Model.builder()
                            .inputLayerId(inputLayerId)
                            .computationGraph(computationGraph)
                            .computationGraphLayerMap(computationGraphLayerMap)
                            .build();
                })
                .collect(Collectors.toList());
    }

    private ComputationGraphConfiguration.GraphBuilder createDefaultConfiguration(){
        NeuralNetConfiguration.Builder builder = new NeuralNetConfiguration.Builder();
        builder.seed(AISingleton.getInstance().getModelManager().getModelParameter().getSeed());
        builder.updater(AISingleton.getInstance().getModelManager().getModelParameter().getIUpdater());
        apply(builder, AISingleton.getInstance().getModelManager().getModelParameter().getBiasInitializer());
        apply(builder, AISingleton.getInstance().getModelManager().getModelParameter().getWeightInitializer());
        builder.miniBatch(false);
        return builder.graphBuilder();
    }

    private void recycleInputPreProcessor(ComputationGraphConfiguration.GraphBuilder graphBuilder, List<org.kok202.dluid.domain.entity.Layer> dluidLayers){
        for (org.kok202.dluid.domain.entity.Layer layer : dluidLayers) {
            InputPreProcessor inputPreProcessor = inputPreProcessorRecycleMap.get(layer.getId());
            if (inputPreProcessor == null)
                continue;
            graphBuilder.inputPreProcessor(layer.getId(), inputPreProcessor);
        }
    }

    private void apply(NeuralNetConfiguration.Builder builder, BiasInitializer biasInitializer){
        if(biasInitializer != null)
            builder.biasInit(biasInitializer.getBias());
    }

    private void apply(NeuralNetConfiguration.Builder builder, WeightInitializer weightInitializer){
        switch(weightInitializer){
            case ZERO:
                builder.weightInit(WeightInit.ZERO);
                break;
            case ONES:
                builder.weightInit(WeightInit.ONES);
                break;
            case NORMAL:
                builder.weightInit(WeightInit.NORMAL);
                break;
            case UNIFORM:
                builder.weightInit(WeightInit.UNIFORM);
                break;
            case XAVIER:
                builder.weightInit(WeightInit.XAVIER);
                break;
            case DISTRIBUTION_ZERO_TO_ONE:
                builder.weightInit(new UniformDistribution(0, 1));
                break;
            case DISTRIBUTION_PLUS_MINUS_ONE:
                builder.weightInit(new UniformDistribution(-1, 1));
                break;
        }
    }
}