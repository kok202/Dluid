package org.kok202.dluid.content.design.material.block;

import org.kok202.dluid.domain.entity.enumerator.LayerType;

public class MaterialPooling2DController extends AbstractMaterialController {
    @Override
    protected void initialize() throws Exception{
        this.setLayerType(LayerType.POOLING_2D);
        super.setStyleByBlockType(layerType);
    }
}
