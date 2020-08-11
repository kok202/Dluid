package org.kokzoz.dluid.ai.network.layer.binder;

import org.deeplearning4j.nn.conf.ComputationGraphConfiguration;
import org.deeplearning4j.nn.conf.graph.ReshapeVertex;
import org.kokzoz.dluid.domain.entity.Layer;
import org.kokzoz.dluid.domain.entity.enumerator.LayerType;

import java.util.List;

public class ReshapeLayerBinder extends AbstractLayerBinder {

    @Override
    public boolean support(Layer layer) {
        return layer.getType() == LayerType.RESHAPE_LAYER;
    }

    @Override
    public void bind(Layer layer, List<Layer> layerFroms, ComputationGraphConfiguration.GraphBuilder graphBuilder) {
        int[] newShape = null;
        if(layer.getProperties().getOutput().getDimension() == 1){
            newShape = new int[]{
                -1,
                layer.getProperties().getOutput().getX()
            };
        }
        else if(layer.getProperties().getOutput().getDimension() == 2){
            if(layer.getProperties().getOutput().isHasChannel())
            newShape = new int[]{
                -1,
                layer.getProperties().getOutput().getYOrChannel(),
                layer.getProperties().getOutput().getX()
            };
        }
        else {
            newShape = new int[]{
                -1,
                layer.getProperties().getOutput().getZOrChannel(),
                layer.getProperties().getOutput().getY(),
                layer.getProperties().getOutput().getX()
            };
        }
        graphBuilder.addVertex(layer.getId(), new ReshapeVertex(newShape), parseLayerIds(layerFroms));
    }

}
