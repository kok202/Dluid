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
        if(layer.getProperties().getOutputDimension().getDimension() == 1){
            newShape = new int[]{
                    -1,
                    layer.getProperties().getOutputSizeX()};
        }
        else if(layer.getProperties().getOutputDimension().getDimension() == 2){
            newShape = new int[]{
                    -1,
                    layer.getProperties().getOutputSizeY(),
                    layer.getProperties().getOutputSizeX()};
        }
        else {
            newShape = new int[]{
                    -1,
                    layer.getProperties().getOutputSizeZ(),
                    layer.getProperties().getOutputSizeY(),
                    layer.getProperties().getOutputSizeX()};
        }
        graphBuilder.addVertex(layer.getId(), new ReshapeVertex(newShape), parseLayerIds(layerFroms));
    }

}
