package org.kok202.dluid.ai.network.layer.binder;

import org.deeplearning4j.nn.conf.ComputationGraphConfiguration;
import org.deeplearning4j.nn.conf.graph.ReshapeVertex;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.entity.enumerator.LayerType;

import java.util.List;

public class ReshapeLayerGenerator extends AbstractLayerGenerator {

    @Override
    public boolean support(Layer layer) {
        return layer.getType() == LayerType.RESHAPE_LAYER;
    }

    @Override
    public void generate(Layer layer, List<Layer> layerFroms, ComputationGraphConfiguration.GraphBuilder graphBuilder) {
        int[] newShape;
        if(layer.getProperties().getOutputSize()[1] == 1){
            newShape = new int[]{
                    -1,
                    layer.getProperties().getOutputSize()[0]};
        }
        else {
            newShape = new int[]{
                    -1,
                    1, //layerInputDepth
                    layer.getProperties().getOutputSize()[1],
                    layer.getProperties().getOutputSize()[0]};
        }
        graphBuilder.addVertex(layer.getId(), new ReshapeVertex(newShape), parseLayerIds(layerFroms));
    }

}
