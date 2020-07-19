package org.kok202.dluid.application.content.design.component;

import org.kok202.dluid.ai.util.ConvolutionCalculatorUtil;
import org.kok202.dluid.application.util.TextFieldUtil;
import org.kok202.dluid.domain.entity.Layer;

public class ComponentDeConvolution2DParamController extends ComponentConvolution2DParamController {

    public ComponentDeConvolution2DParamController(Layer layer) {
        super(layer);
    }

    @Override
    protected int[] getOutputSize(){
        return ConvolutionCalculatorUtil.getDeconv2DOutputSize(
                new int[]{
                        TextFieldUtil.parseInteger(getTextFieldInputSizeX()),
                        TextFieldUtil.parseInteger(getTextFieldInputSizeY())},
                new int[]{
                        TextFieldUtil.parseInteger(getTextFieldKernelSizeX()),
                        TextFieldUtil.parseInteger(getTextFieldKernelSizeY())},
                new int[]{
                        TextFieldUtil.parseInteger(getTextFieldStrideSizeX()),
                        TextFieldUtil.parseInteger(getTextFieldStrideSizeY())},
                new int[]{
                        TextFieldUtil.parseInteger(getTextFieldPaddingSizeX()),
                        TextFieldUtil.parseInteger(getTextFieldPaddingSizeY())});
    }
}
