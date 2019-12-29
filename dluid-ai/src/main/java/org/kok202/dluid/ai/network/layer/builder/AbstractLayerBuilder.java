package org.kok202.dluid.ai.network.layer.builder;

import org.deeplearning4j.nn.conf.layers.BaseLayer.Builder;
import org.kok202.dluid.ai.entity.Layer;

public abstract class AbstractLayerBuilder {

    public abstract boolean support(Layer layer);

    public Builder create(Layer layer){
        Builder builder = createBuilder(layer);
        setCommonProperties(layer, builder);
        setAddOnProperties(layer, builder);
        return builder;
    }

    protected abstract Builder createBuilder(Layer layer);

    protected abstract void setAddOnProperties(Layer layer, Builder builder);

    protected abstract void setCommonProperties(Layer layer, Builder builder);

}
