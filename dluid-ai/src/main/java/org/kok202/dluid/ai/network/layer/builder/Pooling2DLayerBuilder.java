package org.kok202.dluid.ai.network.layer.builder;

import org.deeplearning4j.nn.conf.layers.Layer.Builder;
import org.deeplearning4j.nn.conf.layers.Pooling2D;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.entity.enumerator.LayerType;

public class Pooling2DLayerBuilder extends AbstractLayerBuilder {
    @Override
    public boolean support(Layer layer) {
        return layer.getType() == LayerType.POOLING_2D;
    }

    @Override
    protected Builder createBuilder(Layer layer) {
        return new Pooling2D.Builder();
    }

    @Override
    protected void setAddOnProperties(Layer layer, Builder builder) {
        Pooling2D.Builder pooling2DBuilder = (Pooling2D.Builder) builder;
        if(layer.getProperties().getKernelSize() != null)
            pooling2DBuilder.setKernelSize(layer.getProperties().getKernelSize());
        if(layer.getProperties().getPaddingSize() != null)
            pooling2DBuilder.setPadding(layer.getProperties().getPaddingSize());
        if(layer.getProperties().getStrideSize() != null)
            pooling2DBuilder.setStride(layer.getProperties().getStrideSize());
        if(layer.getProperties().getPoolingType() != null)
            pooling2DBuilder.setPoolingType(layer.getProperties().getPoolingType());
    }

    @Override
    protected void setCommonProperties(Layer layer, Builder builder) {
        Pooling2D.Builder pooling2DBuilder = (Pooling2D.Builder) builder;
        if(layer.getProperties().getDropout() != 0)
            pooling2DBuilder.dropOut(layer.getProperties().getDropout());
    }
}
