package org.kokzoz.dluid.content.design.material.block;

import org.kokzoz.dluid.domain.entity.enumerator.LayerType;

public class MaterialBatchNormalizationController extends AbstractMaterialController {
    @Override
    protected void initialize() throws Exception{
        this.setLayerType(LayerType.BATCH_NORMALIZATION);
        super.setStyleByBlockType(layerType);
    }
}
