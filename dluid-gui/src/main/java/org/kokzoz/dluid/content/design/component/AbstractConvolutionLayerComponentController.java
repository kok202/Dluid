package org.kokzoz.dluid.content.design.component;

import org.kokzoz.dluid.domain.entity.Layer;

public abstract class AbstractConvolutionLayerComponentController extends AbstractLayerComponentController {
    AbstractConvolutionLayerComponentController(Layer layer) {
        super(layer);
    }

    abstract protected void setTextFieldByLayerProperties();
    abstract protected int[] getOutputSize();
}
