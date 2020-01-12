package org.kok202.dluid.ai.network.layer.binder;

import org.deeplearning4j.nn.conf.ComputationGraphConfiguration;
import org.deeplearning4j.nn.conf.layers.BaseLayer.Builder;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.entity.enumerator.LayerType;
import org.kok202.dluid.ai.network.layer.LayerBuilderFactory;

import java.util.List;

public class OutputLayerGenerator extends AbstractLayerGenerator {

    @Override
    public boolean support(Layer layer) {
        return layer.getType() == LayerType.OUTPUT_LAYER;
    }

    @Override
    public void generate(Layer layer, List<Layer> layerFroms, ComputationGraphConfiguration.GraphBuilder graphBuilder) {
        Builder builder = LayerBuilderFactory.create(layer);
        graphBuilder.addLayer(layer.getId(), builder.build(), parseLayerIds(layerFroms));
        graphBuilder.setOutputs(layer.getId());
    }

}
