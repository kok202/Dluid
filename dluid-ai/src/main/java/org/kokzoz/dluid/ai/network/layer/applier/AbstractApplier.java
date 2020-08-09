package org.kokzoz.dluid.ai.network.layer.applier;

import org.deeplearning4j.nn.conf.layers.Layer.Builder;
import org.kokzoz.dluid.domain.entity.LayerProperties;

public abstract class AbstractApplier {
    abstract public boolean support(Builder builder, LayerProperties layerProperties);
    abstract public void apply(Builder builder, LayerProperties layerProperties);
}

// BatchNormalization.Builder batchNormalizationBuilder = (BatchNormalization.Builder) builder;
// Maybe we can set below parameter
//    protected double decay = 0.9;
//    protected double eps = 1e-5;
//    protected boolean isMinibatch = true;
//    protected double gamma = 1.0;
//    protected double beta = 0.0;