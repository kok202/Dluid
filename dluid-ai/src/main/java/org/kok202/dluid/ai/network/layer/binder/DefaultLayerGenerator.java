package org.kok202.dluid.ai.network.layer.binder;

import org.deeplearning4j.nn.conf.ComputationGraphConfiguration;
import org.kok202.dluid.ai.network.layer.LayerFactory;
import org.kok202.dluid.domain.entity.Layer;

import java.util.List;

public class DefaultLayerGenerator extends AbstractLayerGenerator {

    @Override
    public boolean support(Layer layer) {
        return true;
    }

    @Override
    public void generate(Layer layer, List<Layer> layerFroms, ComputationGraphConfiguration.GraphBuilder graphBuilder) {
        graphBuilder.addLayer(layer.getId(), LayerFactory.create(layer), parseLayerIds(layerFroms));
    }

}
