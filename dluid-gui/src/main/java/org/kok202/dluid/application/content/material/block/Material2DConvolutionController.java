package org.kok202.dluid.application.content.material.block;

import org.kok202.dluid.ai.entity.enumerator.LayerType;

public class Material2DConvolutionController extends AbstractMaterialController {
    @Override
    protected void initialize() throws Exception{
        this.setLayerType(LayerType.CONVOLUTION_2D_LAYER);
        super.setStyleByBlockType(layerType);
    }
}
