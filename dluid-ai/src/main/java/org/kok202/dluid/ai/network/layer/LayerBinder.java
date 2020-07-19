package org.kok202.dluid.ai.network.layer;

import org.deeplearning4j.nn.conf.ComputationGraphConfiguration;
import org.kok202.dluid.ai.network.layer.binder.*;
import org.kok202.dluid.domain.entity.Layer;

import java.util.ArrayList;
import java.util.List;

public class LayerBinder {

    private static List<AbstractLayerGenerator> layerGenerators;

    static {
        layerGenerators = new ArrayList<>();
        layerGenerators.add(new InputLayerGenerator());
        layerGenerators.add(new OutputLayerGenerator());
        layerGenerators.add(new MergeLayerGenerator());
        layerGenerators.add(new ReshapeLayerGenerator());
        layerGenerators.add(new NoopLayerGenerator());
        layerGenerators.add(new DefaultLayerGenerator());
    }

    public static void bind(Layer layer, List<Layer> layerFroms, ComputationGraphConfiguration.GraphBuilder graphBuilder){
        for (AbstractLayerGenerator layerGenerator : layerGenerators) {
            if(layerGenerator.support(layer)) {
                layerGenerator.generate(layer, layerFroms, graphBuilder);
                return;
            }
        }
    }
}
