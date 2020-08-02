package org.kok202.dluid.ai.network.layer.builder;

import org.deeplearning4j.nn.conf.layers.Deconvolution2D;
import org.deeplearning4j.nn.conf.layers.Layer.Builder;
import org.kok202.dluid.domain.entity.Layer;
import org.kok202.dluid.domain.entity.enumerator.LayerType;

public class Deconvolution2DLayerBuilder extends AbstractLayerBuilder {
    @Override
    public boolean support(Layer layer) {
        return layer.getType() == LayerType.DECONVOLUTION_2D_LAYER;
    }

    @Override
    protected Builder createBuilder(Layer layer) {
        return new Deconvolution2D.Builder();
    }
}
