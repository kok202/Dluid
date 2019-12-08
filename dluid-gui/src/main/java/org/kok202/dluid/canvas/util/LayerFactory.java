package org.kok202.dluid.canvas.util;

import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.entity.enumerator.LayerType;
import org.kok202.dluid.canvas.entity.InputBlockProperty;
import org.kok202.dluid.canvas.entity.MergeBlockProperty;
import org.kok202.dluid.canvas.entity.SkewedBlockProperty;

public class LayerFactory {

    public static Layer create(LayerType layerType){
        Layer layer = new Layer(layerType);
        switch (layerType){
            case INPUT_LAYER:
                layer.setExtra(new InputBlockProperty());
                break;
            case MERGE_LAYER:
                layer.setExtra(new MergeBlockProperty());
                break;
            default:
                layer.setExtra(new SkewedBlockProperty());
                break;
        }
        return layer;
    }

}
