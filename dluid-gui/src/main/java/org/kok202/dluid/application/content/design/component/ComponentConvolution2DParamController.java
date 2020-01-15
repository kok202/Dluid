package org.kok202.dluid.application.content.design.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.kok202.dluid.AppConstant;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.util.ConvolutionCalculatorUtil;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;
import org.kok202.dluid.application.util.TextFieldUtil;
import org.kok202.dluid.domain.exception.ConvolutionOutputIsNegativeException;

public class ComponentConvolution2DParamController extends AbstractConvolutionLayerComponentController {

    @FXML private Label labelInputOutputWidth;
    @FXML private Label labelInputOutputHeight;
    @FXML private Label labelInputOutputChannel;
    @FXML private Label labelWidth;
    @FXML private Label labelHeight;
    @FXML private Label labelInputSize;
    @FXML private Label labelOutputSize;
    @FXML private Label labelKernelSize;
    @FXML private Label labelStrideSize;
    @FXML private Label labelPaddingSize;

    @FXML private TextField textFieldInputSizeX;
    @FXML private TextField textFieldInputSizeY;
    @FXML private TextField textFieldInputChannelSize;
    @FXML private TextField textFieldKernelSizeX;
    @FXML private TextField textFieldKernelSizeY;
    @FXML private TextField textFieldOutputChannelSize;
    @FXML private TextField textFieldStrideSizeX;
    @FXML private TextField textFieldStrideSizeY;
    @FXML private TextField textFieldPaddingSizeX;
    @FXML private TextField textFieldPaddingSizeY;
    @FXML private TextField textFieldOutputSizeX;
    @FXML private TextField textFieldOutputSizeY;

    public ComponentConvolution2DParamController(Layer layer) {
        super(layer);
    }

    @Override
    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/design/component/convolution2d_param.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        TextFieldUtil.applyPositiveIntegerFilter(textFieldInputSizeX, AppConstant.DEFAULT_INPUT_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldInputSizeY, AppConstant.DEFAULT_INPUT_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldKernelSizeX, AppConstant.DEFAULT_KERNEL_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldKernelSizeY, AppConstant.DEFAULT_KERNEL_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldStrideSizeX, AppConstant.DEFAULT_STRIDE_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldStrideSizeY, AppConstant.DEFAULT_STRIDE_SIZE);
        TextFieldUtil.applyPositiveWithZeroIntegerFilter(textFieldPaddingSizeX, AppConstant.DEFAULT_PADDING_SIZE);
        TextFieldUtil.applyPositiveWithZeroIntegerFilter(textFieldPaddingSizeY, AppConstant.DEFAULT_PADDING_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldInputChannelSize, AppConstant.DEFAULT_CHANNEL_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldOutputChannelSize, AppConstant.DEFAULT_CHANNEL_SIZE);
        setTextFieldByLayerProperties();

        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.title"));
        labelInputOutputWidth.setText(AppPropertiesSingleton.getInstance().get("frame.component.width"));
        labelInputOutputHeight.setText(AppPropertiesSingleton.getInstance().get("frame.component.height"));
        labelInputOutputChannel.setText(AppPropertiesSingleton.getInstance().get("frame.component.channel"));
        labelWidth.setText(AppPropertiesSingleton.getInstance().get("frame.component.width"));
        labelHeight.setText(AppPropertiesSingleton.getInstance().get("frame.component.height"));

        labelInputSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.inputSize"));
        labelOutputSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.outputSize"));
        labelKernelSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.convolution.kernelSize"));
        labelStrideSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.convolution.strideSize"));
        labelPaddingSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.convolution.paddingSize"));
    }

    @Override
    protected void setTextFieldByLayerProperties(){
        detachTextChangedListener(
                textFieldInputSizeX, textFieldKernelSizeX, textFieldStrideSizeX, textFieldPaddingSizeX,
                textFieldInputSizeY, textFieldKernelSizeY, textFieldStrideSizeY, textFieldPaddingSizeY,
                textFieldInputChannelSize, textFieldOutputChannelSize);
        textFieldInputSizeX.setText(String.valueOf(layer.getProperties().getInputSize()[0]));
        textFieldInputSizeY.setText(String.valueOf(layer.getProperties().getInputSize()[1]));
        textFieldInputChannelSize.setText(String.valueOf(layer.getProperties().getInputSize()[2]));
        textFieldOutputSizeX.setText(String.valueOf(layer.getProperties().getOutputSize()[0]));
        textFieldOutputSizeY.setText(String.valueOf(layer.getProperties().getOutputSize()[1]));
        textFieldOutputChannelSize.setText(String.valueOf(layer.getProperties().getOutputSize()[2]));
        textFieldStrideSizeX.setText(String.valueOf(layer.getProperties().getStrideSize()[0]));
        textFieldStrideSizeY.setText(String.valueOf(layer.getProperties().getStrideSize()[1]));
        textFieldPaddingSizeX.setText(String.valueOf(layer.getProperties().getPaddingSize()[0]));
        textFieldPaddingSizeY.setText(String.valueOf(layer.getProperties().getPaddingSize()[1]));
        textFieldKernelSizeX.setText(String.valueOf(layer.getProperties().getKernelSize()[0]));
        textFieldKernelSizeY.setText(String.valueOf(layer.getProperties().getKernelSize()[1]));
        attachTextChangedListener(
                textFieldInputSizeX, textFieldKernelSizeX, textFieldStrideSizeX, textFieldPaddingSizeX,
                textFieldInputSizeY, textFieldKernelSizeY, textFieldStrideSizeY, textFieldPaddingSizeY,
                textFieldInputChannelSize, textFieldOutputChannelSize);
    }

    @Override
    protected void textFieldChangedHandler(){
        int[] outputSize= getOutputSize();
        if(outputSize[0] <= 0 || outputSize[1] <= 0){
            setTextFieldByLayerProperties();
            throw new ConvolutionOutputIsNegativeException(outputSize);
        }

        layer.getProperties().setInputSize(
                TextFieldUtil.parseInteger(textFieldInputSizeX),
                TextFieldUtil.parseInteger(textFieldInputSizeY),
                TextFieldUtil.parseInteger(textFieldInputChannelSize));
        layer.getProperties().setKernelSize(new int[]{
                TextFieldUtil.parseInteger(textFieldKernelSizeX),
                TextFieldUtil.parseInteger(textFieldKernelSizeY)});
        layer.getProperties().setStrideSize(new int[]{
                TextFieldUtil.parseInteger(textFieldStrideSizeX),
                TextFieldUtil.parseInteger(textFieldStrideSizeY)});
        layer.getProperties().setPaddingSize(new int[]{
                TextFieldUtil.parseInteger(textFieldPaddingSizeX),
                TextFieldUtil.parseInteger(textFieldPaddingSizeY)});
        layer.getProperties().setOutputSize(
                outputSize[0],
                outputSize[1],
                TextFieldUtil.parseInteger(textFieldOutputChannelSize));
        textFieldOutputSizeX.setText(String.valueOf(layer.getProperties().getOutputSize()[0]));
        textFieldOutputSizeY.setText(String.valueOf(layer.getProperties().getOutputSize()[1]));
        notifyLayerDataChanged();
    }

    @Override
    protected int[] getOutputSize(){
        return ConvolutionCalculatorUtil.getConv2DOutputSize(
                new int[]{
                        TextFieldUtil.parseInteger(textFieldInputSizeX),
                        TextFieldUtil.parseInteger(textFieldInputSizeY)},
                new int[]{
                        TextFieldUtil.parseInteger(textFieldKernelSizeX),
                        TextFieldUtil.parseInteger(textFieldKernelSizeY)},
                new int[]{
                        TextFieldUtil.parseInteger(textFieldStrideSizeX),
                        TextFieldUtil.parseInteger(textFieldStrideSizeY)},
                new int[]{
                        TextFieldUtil.parseInteger(textFieldPaddingSizeX),
                        TextFieldUtil.parseInteger(textFieldPaddingSizeY)});
    }
}
