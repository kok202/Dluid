package org.kok202.deepblock.application.content.material.block;

import org.kok202.deepblock.ai.entity.enumerator.LayerType;

public class Material1DConvolutionController extends AbstractMaterialController {
    @Override
    protected void initialize() throws Exception{
        this.setLayerType(LayerType.CONVOLUTION_1D_LAYER);
        super.setStyleByBlockType(layerType);
    }
}
