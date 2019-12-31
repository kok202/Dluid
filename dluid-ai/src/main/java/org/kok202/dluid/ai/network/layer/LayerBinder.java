package org.kok202.dluid.ai.network.layer;

import org.deeplearning4j.nn.conf.NeuralNetConfiguration.ListBuilder;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.network.layer.binder.AbstractLayerGenerator;
import org.kok202.dluid.ai.network.layer.binder.DefaultLayerGenerator;
import org.kok202.dluid.ai.network.layer.binder.NoopLayerGenerator;
import org.kok202.dluid.domain.structure.GraphNode;

import java.util.ArrayList;
import java.util.List;

public class LayerBinder {

    private static List<AbstractLayerGenerator> layerGenerators;

    static {
        layerGenerators = new ArrayList<>();
        layerGenerators.add(new NoopLayerGenerator());
        layerGenerators.add(new DefaultLayerGenerator());
    }

    public static void bind(GraphNode<Layer> currentLayerGraphNode, ListBuilder neuralNetBuilder){
        for (AbstractLayerGenerator layerGenerator : layerGenerators) {
            if(layerGenerator.support(currentLayerGraphNode)) {
                layerGenerator.generate(currentLayerGraphNode, neuralNetBuilder);
                return;
            }
        }
    }
}
