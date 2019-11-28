package org.kok202.dluid.application.content.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.kok202.dluid.AppConstant;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.util.ConvolutionCalculatorUtil;
import org.kok202.dluid.application.Util.TextFieldUtil;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;

public class ComponentConvolution2DParamController extends AbstractConvolutionLayerComponentController {

    @FXML private Label labelWidth;
    @FXML private Label labelHeight;
    @FXML private Label labelInputSize;
    @FXML private Label labelOutputSize;
    @FXML private Label labelKernelSize;
    @FXML private Label labelStrideSize;
    @FXML private Label labelPaddingSize;

    @FXML private TextField textFieldInputSizeX;
    @FXML private TextField textFieldInputSizeY;
    @FXML private TextField textFieldKernelSizeX;
    @FXML private TextField textFieldKernelSizeY;
    @FXML private TextField textFieldStrideSizeX;
    @FXML private TextField textFieldStrideSizeY;
    @FXML private TextField textFieldPaddingSizeX;
    @FXML private TextField textFieldPaddingSizeY;
    @FXML private TextField textFieldOutputSizeValueX;
    @FXML private TextField textFieldOutputSizeValueY;

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
        setTextFieldByLayerProperties();

        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.title"));
        labelWidth.setText(AppPropertiesSingleton.getInstance().get("frame.component.2d.width"));
        labelHeight.setText(AppPropertiesSingleton.getInstance().get("frame.component.2d.height"));
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
                textFieldInputSizeY, textFieldKernelSizeY, textFieldStrideSizeY, textFieldPaddingSizeY);
        textFieldInputSizeX.setText(String.valueOf(layer.getProperties().getInputSize()[0]));
        textFieldInputSizeY.setText(String.valueOf(layer.getProperties().getInputSize()[1]));
        textFieldOutputSizeValueX.setText(String.valueOf(layer.getProperties().getOutputSize()[0]));
        textFieldOutputSizeValueY.setText(String.valueOf(layer.getProperties().getOutputSize()[1]));
        textFieldStrideSizeX.setText(String.valueOf(layer.getProperties().getStrideSize()[0]));
        textFieldStrideSizeY.setText(String.valueOf(layer.getProperties().getStrideSize()[1]));
        textFieldPaddingSizeX.setText(String.valueOf(layer.getProperties().getPaddingSize()[0]));
        textFieldPaddingSizeY.setText(String.valueOf(layer.getProperties().getPaddingSize()[1]));
        textFieldKernelSizeX.setText(String.valueOf(layer.getProperties().getKernelSize()[0]));
        textFieldKernelSizeY.setText(String.valueOf(layer.getProperties().getKernelSize()[1]));
        attachTextChangedListener(
                textFieldInputSizeX, textFieldKernelSizeX, textFieldStrideSizeX, textFieldPaddingSizeX,
                textFieldInputSizeY, textFieldKernelSizeY, textFieldStrideSizeY, textFieldPaddingSizeY);
    }

    @Override
    protected void textFieldChangedHandler(){
        int[] outputSize= getOutputSize();
        if(outputSize[0] <= 0 || outputSize[1] <= 0){
            setTextFieldByLayerProperties();
            showOutputSizeErrorDialog(outputSize);
            return;
        }

        layer.getProperties().setInputSize(
                TextFieldUtil.parseInteger(textFieldInputSizeX),
                TextFieldUtil.parseInteger(textFieldInputSizeY));
        layer.getProperties().setKernelSize(new int[]{
                TextFieldUtil.parseInteger(textFieldKernelSizeX),
                TextFieldUtil.parseInteger(textFieldKernelSizeY)});
        layer.getProperties().setStrideSize(new int[]{
                TextFieldUtil.parseInteger(textFieldStrideSizeX),
                TextFieldUtil.parseInteger(textFieldStrideSizeY)});
        layer.getProperties().setPaddingSize(new int[]{
                TextFieldUtil.parseInteger(textFieldPaddingSizeX),
                TextFieldUtil.parseInteger(textFieldPaddingSizeY)});

        textFieldOutputSizeValueX.setText(String.valueOf(outputSize[0]));
        textFieldOutputSizeValueY.setText(String.valueOf(outputSize[1]));
        layer.getProperties().setOutputSize(outputSize[0], outputSize[1]);
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
