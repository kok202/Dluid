package org.kok202.deepblock.canvas.util;

import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.ai.entity.enumerator.LayerType;
import org.kok202.deepblock.canvas.entity.MergeBlockProperty;
import org.kok202.deepblock.canvas.entity.SkewedBlockProperty;

public class LayerFactory {

    public static Layer create(LayerType layerType){
        Layer layer = new Layer(layerType);
        switch (layerType){
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
