package org.kok202.dluid.application.content.design.component;

import org.kok202.dluid.ai.entity.Layer;

public abstract class AbstractConvolutionLayerComponentController extends AbstractLayerComponentController {
    AbstractConvolutionLayerComponentController(Layer layer) {
        super(layer);
    }

    abstract protected void setTextFieldByLayerProperties();
    abstract protected int[] getOutputSize();
}
