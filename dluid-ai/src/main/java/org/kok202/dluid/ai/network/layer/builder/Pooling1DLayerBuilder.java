package org.kok202.dluid.ai.network.layer.builder;

import org.deeplearning4j.nn.conf.layers.Layer.Builder;
import org.deeplearning4j.nn.conf.layers.Pooling1D;
import org.kok202.dluid.ai.util.PoolingTypeUtil;
import org.kok202.dluid.domain.entity.Layer;
import org.kok202.dluid.domain.entity.enumerator.LayerType;

public class Pooling1DLayerBuilder extends AbstractLayerBuilder {
    @Override
    public boolean support(Layer layer) {
        return layer.getType() == LayerType.POOLING_1D;
    }

    @Override
    protected Builder createBuilder(Layer layer) {
        return new Pooling1D.Builder();
    }

    @Override
    protected void setAddOnProperties(Layer layer, Builder builder) {
        Pooling1D.Builder pooling1DBuilder = (Pooling1D.Builder) builder;
        if(layer.getProperties().getKernelSize() != null)
            pooling1DBuilder.setKernelSize(layer.getProperties().getKernelSize());
        if(layer.getProperties().getPaddingSize() != null)
            pooling1DBuilder.setPadding(layer.getProperties().getPaddingSize());
        if(layer.getProperties().getStrideSize() != null)
            pooling1DBuilder.setStride(layer.getProperties().getStrideSize());
        if(layer.getProperties().getPoolingType() != null)
            pooling1DBuilder.setPoolingType(PoolingTypeUtil.get(layer.getProperties().getPoolingType()));
    }

    @Override
    protected void setCommonProperties(Layer layer, Builder builder) {
        Pooling1D.Builder pooling1DBuilder = (Pooling1D.Builder) builder;
        if(layer.getProperties().getDropout() != 0)
            pooling1DBuilder.dropOut(layer.getProperties().getDropout());
    }
}
