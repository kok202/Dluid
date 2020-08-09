package org.kokzoz.dluid.content.design.material.block;

import org.kokzoz.dluid.domain.entity.enumerator.LayerType;

public class Material1DConvolutionController extends AbstractMaterialController {
    @Override
    protected void initialize() throws Exception{
        this.setLayerType(LayerType.CONVOLUTION_1D_LAYER);
        super.setStyleByBlockType(layerType);
    }
}
