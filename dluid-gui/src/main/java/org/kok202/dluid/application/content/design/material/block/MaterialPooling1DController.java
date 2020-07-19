package org.kok202.dluid.application.content.design.material.block;

import org.kok202.dluid.domain.entity.enumerator.LayerType;

public class MaterialPooling1DController extends AbstractMaterialController {
    @Override
    protected void initialize() throws Exception{
        this.setLayerType(LayerType.POOLING_1D);
        super.setStyleByBlockType(layerType);
    }
}
