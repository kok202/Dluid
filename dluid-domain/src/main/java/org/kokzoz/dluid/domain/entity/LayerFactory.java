package org.kokzoz.dluid.domain.entity;

import org.kokzoz.dluid.domain.entity.enumerator.LayerType;

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
