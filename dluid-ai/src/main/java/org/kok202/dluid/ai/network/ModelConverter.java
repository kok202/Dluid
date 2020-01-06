package org.kok202.dluid.ai.network;

import org.deeplearning4j.nn.conf.InputPreProcessor;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.kok202.dluid.ai.network.layer.LayerBinder;
import org.kok202.dluid.ai.singleton.AISingleton;

import java.util.*;
import java.util.stream.Collectors;

public class ModelConverter {
    private Map<String, InputPreProcessor> inputPreProcessorRecycleMap;

    public ModelConverter() {
        this.inputPreProcessorRecycleMap = new HashMap<>();
    }

    public List<Model> convert(GraphManagerAnalyzer graphManagerAnalyzer){
        return graphManagerAnalyzer.getInputLayerIds()
                .stream()
                .map(inputLayerId -> {
                    Map<String, org.kok202.dluid.ai.entity.Layer> dluidLayerMap = graphManagerAnalyzer.getMultiLayerManager().get(inputLayerId);
                    List<org.kok202.dluid.ai.entity.Layer> dluidLayers = new ArrayList<>(dluidLayerMap.values());

                    NeuralNetConfiguration.ListBuilder listBuilder = createDefaultConfiguration();
                    dluidLayers.forEach(dluidLayer -> LayerBinder.bind(dluidLayer, listBuilder));
                    recycleInputPreProcessor(listBuilder, dluidLayers);

                    MultiLayerConfiguration multiLayerConfiguration = listBuilder.build();
                    MultiLayerNetwork multiLayerNetwork = new MultiLayerNetwork(multiLayerConfiguration);
                    multiLayerNetwork.init();

                    Map<String, NeuralNetLayer> NeuralNetLayerMap = new LinkedHashMap<>();
                    for (int i = 0; i < dluidLayers.size(); i++) {
                        String dluidLayerId = dluidLayers.get(i).getId();
                        NeuralNetLayerMap.put(
                                dluidLayerId,
                                NeuralNetLayer.builder()
                                        .sequence(i)
                                        .layer(multiLayerNetwork.getLayers()[i])
                                        .build());
                        inputPreProcessorRecycleMap.put(dluidLayerId, multiLayerConfiguration.getInputPreProcess(i));
                    }

                    return Model.builder()
                            .inputLayerId(inputLayerId)
                            .multiLayerNetwork(multiLayerNetwork)
                            .multiLayerConfLayerMap(NeuralNetLayerMap)
                            .build();
                })
                .collect(Collectors.toList());
    }

    private NeuralNetConfiguration.ListBuilder createDefaultConfiguration(){
        return new NeuralNetConfiguration.Builder()
                .seed(AISingleton.getInstance().getModelManager().getModelParameter().getSeed())
                .updater(AISingleton.getInstance().getModelManager().getModelParameter().getIUpdater())
                .weightInit(AISingleton.getInstance().getModelManager().getModelParameter().getWeightInit())
                .miniBatch(false)
                .list();
    }

    private void recycleInputPreProcessor(NeuralNetConfiguration.ListBuilder listBuilder, List<org.kok202.dluid.ai.entity.Layer> dluidLayers){
        for (int i = 0; i < dluidLayers.size(); i++) {
            org.kok202.dluid.ai.entity.Layer layer = dluidLayers.get(i);
            InputPreProcessor inputPreProcessor = inputPreProcessorRecycleMap.get(layer.getId());
            if(inputPreProcessor == null)
                continue;
            listBuilder.inputPreProcessor(i, inputPreProcessor);
        }
    }
}