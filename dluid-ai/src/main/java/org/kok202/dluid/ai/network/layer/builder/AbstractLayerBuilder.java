package org.kok202.dluid.ai.network.layer.builder;

import org.deeplearning4j.nn.conf.layers.Layer.Builder;
import org.kok202.dluid.ai.network.layer.applier.*;
import org.kok202.dluid.domain.entity.Layer;

public abstract class AbstractLayerBuilder {

    public static AbstractApplier[] appliers = new AbstractApplier[]{
        new ActivationApplier(),
        new BiasInitApplier(),
        new DropoutApplier(),
        new InputApplier(),
        new KernelSizeApplier(),
        new LossFunctionApplier(),
        new OutputApplier(),
        new PaddingSizeApplier(),
        new PoolingTypeApplier(),
        new StrideSizeApplier(),
        new WeightInitApplier(),
    };

    public abstract boolean support(Layer layer);

    public org.deeplearning4j.nn.conf.layers.Layer build(Layer layer){
        Builder builder = createBuilder(layer);
        for(AbstractApplier applier : appliers){
            if(applier.support(builder, layer.getProperties())){
                applier.apply(builder, layer.getProperties());
            }
        }
        return builder.build();
    }

    protected abstract Builder createBuilder(Layer layer);
}
