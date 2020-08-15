package org.kokzoz.dluid.ai.network.layer.binder;

import org.apache.commons.lang3.ArrayUtils;
import org.deeplearning4j.nn.conf.ComputationGraphConfiguration;
import org.deeplearning4j.nn.conf.graph.ReshapeVertex;
import org.kokzoz.dluid.domain.entity.Layer;
import org.kokzoz.dluid.domain.entity.enumerator.LayerType;

import java.util.List;

public class ReshapeLayerBinder extends AbstractLayerBinder {

    private static final int FLEXIBLE_BATCH_SIZE = -1;

    @Override
    public boolean support(Layer layer) {
        return layer.getType() == LayerType.RESHAPE_LAYER;
    }

    @Override
    public void bind(Layer layer, List<Layer> layerFroms, ComputationGraphConfiguration.GraphBuilder graphBuilder) {
        int[] newShape = ArrayUtils.addAll(layer.getProperties().getOutput().asArray(), FLEXIBLE_BATCH_SIZE);
        ArrayUtils.reverse(newShape);
        graphBuilder.addVertex(layer.getId(), new ReshapeVertex(newShape), parseLayerIds(layerFroms));
    }

}
