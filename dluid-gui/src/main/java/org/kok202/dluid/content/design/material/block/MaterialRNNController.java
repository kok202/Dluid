package org.kok202.dluid.content.design.material.block;

import org.kok202.dluid.domain.entity.enumerator.LayerType;

public class MaterialRNNController extends AbstractMaterialController {
    @Override
    protected void initialize() throws Exception{
        this.setLayerType(LayerType.BASE_RECURRENT_LAYER);
        super.setStyleByBlockType(layerType);
    }
}
