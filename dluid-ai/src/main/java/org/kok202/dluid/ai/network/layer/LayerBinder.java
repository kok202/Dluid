package org.kok202.dluid.ai.network.layer;

import org.deeplearning4j.nn.conf.ComputationGraphConfiguration.GraphBuilder;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.network.layer.binder.*;
import org.kok202.dluid.domain.structure.GraphNode;

import java.util.ArrayList;
import java.util.List;

public class LayerBinder {

    private static List<AbstractLayerGenerator> layerGenerators;

    static {
        layerGenerators = new ArrayList<>();
        layerGenerators.add(new NoopLayerGenerator());
        layerGenerators.add(new InputLayerGenerator());
        layerGenerators.add(new MergeLayerGenerator());
        layerGenerators.add(new OutputLayerGenerator());
        layerGenerators.add(new DefaultLayerGenerator());
    }

    public static void bind(GraphNode<Layer> currentLayerGraphNode, GraphBuilder graphBuilder){
        for (AbstractLayerGenerator layerGenerator : layerGenerators) {
            if(layerGenerator.support(currentLayerGraphNode)) {
                layerGenerator.generate(currentLayerGraphNode, graphBuilder);
                return;
            }
        }
    }
}
