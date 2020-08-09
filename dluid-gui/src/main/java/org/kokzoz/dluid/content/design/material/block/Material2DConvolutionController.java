package org.kokzoz.dluid.content.design.material.block;

import org.kokzoz.dluid.domain.entity.enumerator.LayerType;

public class Material2DConvolutionController extends AbstractMaterialController {
    @Override
    protected void initialize() throws Exception{
        this.setLayerType(LayerType.CONVOLUTION_2D_LAYER);
        super.setStyleByBlockType(layerType);
    }
}
