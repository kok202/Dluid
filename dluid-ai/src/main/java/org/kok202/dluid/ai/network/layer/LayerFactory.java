package org.kok202.dluid.ai.network.layer;

import org.kok202.dluid.ai.network.layer.builder.*;
import org.kok202.dluid.domain.entity.Layer;
import org.kok202.dluid.domain.exception.IllegalLayerRequest;

import java.util.ArrayList;
import java.util.List;

public class LayerFactory {

    private static List<AbstractLayerBuilder> baseLayerBuilders;

    static {
        baseLayerBuilders = new ArrayList<>();
        baseLayerBuilders.add(new DenseLayerBuilder());
        baseLayerBuilders.add(new Convolution1DLayerBuilder());
        baseLayerBuilders.add(new Convolution2DLayerBuilder());
        baseLayerBuilders.add(new Deconvolution2DLayerBuilder());
        baseLayerBuilders.add(new LSTMLayerBuilder());
        baseLayerBuilders.add(new RNNLayerBuilder());
        baseLayerBuilders.add(new OutputLayerBuilder());
        baseLayerBuilders.add(new Pooling1DLayerBuilder());
        baseLayerBuilders.add(new Pooling2DLayerBuilder());
        baseLayerBuilders.add(new BatchNormalizationLayerBuilder());
        baseLayerBuilders.add(new RNNOutputLayerBuilder());
    }

    public static org.deeplearning4j.nn.conf.layers.Layer create(Layer layer){
        for (AbstractLayerBuilder baseLayerBuilder : baseLayerBuilders) {
            if(baseLayerBuilder.support(layer))
                return baseLayerBuilder.build(layer);
        }
        throw new IllegalLayerRequest();
    }
}
