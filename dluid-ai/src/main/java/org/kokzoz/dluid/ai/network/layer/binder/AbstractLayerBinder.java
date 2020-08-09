package org.kokzoz.dluid.ai.network.layer.binder;

import org.deeplearning4j.nn.conf.ComputationGraphConfiguration;
import org.kokzoz.dluid.domain.entity.Layer;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractLayerBinder {

    public abstract boolean support(Layer layer);
    public abstract void bind(Layer layer, List<Layer> layerFroms, ComputationGraphConfiguration.GraphBuilder graphBuilder);
    public String[] parseLayerIds(List<Layer> layers) {
        return layers.stream().map(Layer::getId).collect(Collectors.toList()).toArray(new String[1]);
    }
}
