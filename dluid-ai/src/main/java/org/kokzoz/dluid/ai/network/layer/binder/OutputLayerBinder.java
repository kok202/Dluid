package org.kokzoz.dluid.ai.network.layer.binder;

import org.deeplearning4j.nn.conf.ComputationGraphConfiguration;
import org.kokzoz.dluid.ai.network.layer.LayerBuilderManager;
import org.kokzoz.dluid.domain.entity.Layer;

import java.util.List;

public class OutputLayerBinder extends AbstractLayerBinder {

    @Override
    public boolean support(Layer layer) {
        return layer.getType().isOutputLayerType();
    }

    @Override
    public void bind(Layer layer, List<Layer> layerFroms, ComputationGraphConfiguration.GraphBuilder graphBuilder) {
        graphBuilder.addLayer(layer.getId(), LayerBuilderManager.create(layer), parseLayerIds(layerFroms));
        graphBuilder.setOutputs(layer.getId());
    }

}
