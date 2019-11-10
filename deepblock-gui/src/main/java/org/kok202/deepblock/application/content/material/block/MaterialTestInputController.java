package org.kok202.deepblock.application.content.material.block;

import org.kok202.deepblock.ai.entity.enumerator.LayerType;

public class MaterialTestInputController extends AbstractMaterialController {
    @Override
    protected void initialize() throws Exception{
        this.setLayerType(LayerType.TEST_INPUT_LAYER);
        super.setStyleByBlockType(layerType);
    }
}
