package org.kok202.deepblock.application.content.material.block;

import org.kok202.deepblock.ai.entity.enumerator.LayerType;

public class Material1DFeedForwardController extends AbstractMaterialController {
    @Override
    protected void initialize() throws Exception{
        this.setLayerType(LayerType.DENSE_LAYER);
        super.setStyleByBlockType(layerType);
    }
}
