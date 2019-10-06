package org.kok202.deepblock.application.content.material.block;

import org.kok202.deepblock.ai.entity.enumerator.LayerType;

public class MaterialInputController extends AbstractMaterialController {
    @Override
    protected void initialize() throws Exception{
        this.setLayerType(LayerType.INPUT_LAYER);
        super.setStyleByBlockType(layerType);
    }
}
