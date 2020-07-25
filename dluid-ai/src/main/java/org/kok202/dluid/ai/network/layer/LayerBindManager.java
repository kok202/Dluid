package org.kok202.dluid.ai.network.layer;

import org.deeplearning4j.nn.conf.ComputationGraphConfiguration;
import org.kok202.dluid.ai.network.layer.binder.*;
import org.kok202.dluid.domain.entity.Layer;

import java.util.ArrayList;
import java.util.List;

public class LayerBindManager {

    private static List<AbstractLayerBinder> layerBinders;

    static {
        layerBinders = new ArrayList<>();
        layerBinders.add(new InputLayerBinder());
        layerBinders.add(new OutputLayerBinder());
        layerBinders.add(new MergeLayerBinder());
        layerBinders.add(new ReshapeLayerBinder());
        layerBinders.add(new NoopLayerBinder());
        layerBinders.add(new DefaultLayerBinder());
    }

    public static void bind(Layer layer, List<Layer> layerFroms, ComputationGraphConfiguration.GraphBuilder graphBuilder){
        for (AbstractLayerBinder layerBinder : layerBinders) {
            if(layerBinder.support(layer)) {
                layerBinder.bind(layer, layerFroms, graphBuilder);
                return;
            }
        }
    }
}
