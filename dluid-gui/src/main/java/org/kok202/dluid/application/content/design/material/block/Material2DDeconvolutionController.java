package org.kok202.dluid.application.content.design.material.block;

import org.kok202.dluid.domain.entity.enumerator.LayerType;

public class Material2DDeconvolutionController extends AbstractMaterialController {
    @Override
    protected void initialize() throws Exception{
        this.setLayerType(LayerType.DECONVOLUTION_2D_LAYER);
        super.setStyleByBlockType(layerType);
    }
}
