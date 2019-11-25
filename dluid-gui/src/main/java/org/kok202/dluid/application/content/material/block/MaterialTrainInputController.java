package org.kok202.dluid.application.content.material.block;

import org.kok202.dluid.ai.entity.enumerator.LayerType;

public class MaterialTrainInputController extends AbstractMaterialController {
    @Override
    protected void initialize() throws Exception{
        this.setLayerType(LayerType.TRAIN_INPUT_LAYER);
        super.setStyleByBlockType(layerType);
    }
}
