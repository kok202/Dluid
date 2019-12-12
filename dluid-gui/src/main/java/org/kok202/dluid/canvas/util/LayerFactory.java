package org.kok202.dluid.canvas.util;

import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.entity.enumerator.LayerType;

public class LayerFactory {

    public static Layer create(LayerType layerType){
        Layer layer = new Layer(layerType);
        switch (layerType){
            default:
                break;
        }
        return layer;
    }

}
