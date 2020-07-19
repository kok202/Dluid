package org.kok202.dluid.ai.network.layer.builder;

import org.deeplearning4j.nn.conf.layers.BatchNormalization;
import org.deeplearning4j.nn.conf.layers.Layer.Builder;
import org.kok202.dluid.ai.util.ActivationUtil;
import org.kok202.dluid.ai.util.BiasInitUtil;
import org.kok202.dluid.ai.util.WeightInitUtil;
import org.kok202.dluid.domain.entity.Layer;
import org.kok202.dluid.domain.entity.enumerator.LayerType;

public class BatchNormalizationLayerBuilder extends AbstractLayerBuilder {
    @Override
    public boolean support(Layer layer) {
        return layer.getType() == LayerType.BATCH_NORMALIZATION;
    }

    @Override
    protected Builder createBuilder(Layer layer) {
        return new BatchNormalization.Builder();
    }

    @Override
    protected void setAddOnProperties(Layer layer, Builder builder) {
        BatchNormalization.Builder batchNormalizationBuilder = (BatchNormalization.Builder) builder;
        // Maybe we can set below parameter
        //    protected double decay = 0.9;
        //    protected double eps = 1e-5;
        //    protected boolean isMinibatch = true;
        //    protected double gamma = 1.0;
        //    protected double beta = 0.0;
    }

    @Override
    protected void setCommonProperties(Layer layer, Builder builder) {
        BatchNormalization.Builder batchNormalizationBuilder = (BatchNormalization.Builder) builder;
        if(layer.getProperties().getInputSize() != null)
            batchNormalizationBuilder.nIn(layer.getProperties().getInputVolume());
        if(layer.getProperties().getOutputSize() != null)
            batchNormalizationBuilder.nOut(layer.getProperties().getOutputVolume());
        if(layer.getProperties().getBiasInitializer() != null)
            BiasInitUtil.applyBiasInit(batchNormalizationBuilder, layer.getProperties().getBiasInitializer());
        if(layer.getProperties().getWeightInitializer() != null)
            WeightInitUtil.applyWeightInit(batchNormalizationBuilder, layer.getProperties().getWeightInitializer());
        if(layer.getProperties().getActivationFunction() != null)
            batchNormalizationBuilder.activation(ActivationUtil.get(layer.getProperties().getActivationFunction()));
        if(layer.getProperties().getDropout() != 0)
            batchNormalizationBuilder.dropOut(layer.getProperties().getDropout());
    }
}
