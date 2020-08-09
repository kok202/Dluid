package org.kokzoz.dluid.ai.network.layer.binder;

import org.deeplearning4j.nn.conf.ComputationGraphConfiguration;
import org.kokzoz.dluid.ai.network.layer.LayerBuilderManager;
import org.kokzoz.dluid.domain.entity.Layer;

import java.util.List;

public class DefaultLayerBinder extends AbstractLayerBinder {

    @Override
    public boolean support(Layer layer) {
        return true;
    }

    @Override
    public void bind(Layer layer, List<Layer> layerFroms, ComputationGraphConfiguration.GraphBuilder graphBuilder) {
        graphBuilder.addLayer(layer.getId(), LayerBuilderManager.create(layer), parseLayerIds(layerFroms));
    }

}
