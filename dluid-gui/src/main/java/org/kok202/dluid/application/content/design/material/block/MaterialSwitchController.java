package org.kok202.dluid.application.content.design.material.block;

import org.kok202.dluid.domain.entity.enumerator.LayerType;

public class MaterialSwitchController extends AbstractMaterialController {
    @Override
    protected void initialize() throws Exception{
        this.setLayerType(LayerType.SWITCH_LAYER);
        super.setStyleByBlockType(layerType);
    }
}
