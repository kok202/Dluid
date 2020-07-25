package org.kok202.dluid.ai.network.layer.binder;

import org.deeplearning4j.nn.conf.ComputationGraphConfiguration;
import org.deeplearning4j.nn.conf.graph.MergeVertex;
import org.kok202.dluid.domain.entity.Layer;
import org.kok202.dluid.domain.entity.enumerator.LayerType;

import java.util.List;

public class MergeLayerBinder extends AbstractLayerBinder {
    @Override
    public boolean support(Layer layer) {
        return layer.getType() == LayerType.MERGE_LAYER;
    }

    @Override
    public void bind(Layer layer, List<Layer> layerFroms, ComputationGraphConfiguration.GraphBuilder graphBuilder) {
        graphBuilder.addVertex(layer.getId(), new MergeVertex(), parseLayerIds(layerFroms));
    }

}
