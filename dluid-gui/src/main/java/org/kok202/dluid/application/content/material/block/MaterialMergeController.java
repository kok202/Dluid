package org.kok202.dluid.application.content.material.block;

import org.kok202.dluid.ai.entity.enumerator.LayerType;

public class MaterialMergeController extends AbstractMaterialController {
    @Override
    protected void initialize() throws Exception{
        this.setLayerType(LayerType.MERGE_LAYER);
        super.setStyleByBlockType(layerType);
    }
}
