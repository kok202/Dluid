package org.kok202.dluid.application.content.design.material.block;

import org.kok202.dluid.domain.entity.enumerator.LayerType;

public class MaterialReshapeController extends AbstractMaterialController {
    @Override
    protected void initialize() throws Exception{
        this.setLayerType(LayerType.RESHAPE_LAYER);
        super.setStyleByBlockType(layerType);
    }
}
