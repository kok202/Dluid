package org.kok202.dluid.ai.network.layer;

import org.deeplearning4j.nn.conf.layers.BaseLayer.Builder;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.network.layer.builder.*;
import org.kok202.dluid.domain.exception.IllegalLayerRequest;

import java.util.ArrayList;
import java.util.List;

public class LayerBuilderFactory {

    private static List<AbstractLayerBuilder> layerBuilders;

    static {
        layerBuilders = new ArrayList<>();
        layerBuilders.add(new DenseLayerBuilder());
        layerBuilders.add(new Convolution1DLayerBuilder());
        layerBuilders.add(new Convolution2DLayerBuilder());
        layerBuilders.add(new Deconvolution2DLayerBuilder());
        layerBuilders.add(new LSTMLayerBuilder());
        layerBuilders.add(new RNNLayerBuilder());
        layerBuilders.add(new OutputLayerBuilder());
    }

    public static Builder create(Layer layer){
        for (AbstractLayerBuilder layerBuilder : layerBuilders) {
            if(layerBuilder.support(layer))
                return layerBuilder.create(layer);
        }
        throw new IllegalLayerRequest();
    }
}
