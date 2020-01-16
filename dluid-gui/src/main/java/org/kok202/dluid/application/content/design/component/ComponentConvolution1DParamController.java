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

public class ComponentConvolution1DParamController extends AbstractConvolutionLayerComponentController {

    @FXML private Label labelInputOutputX;
    @FXML private Label labelInputOutputY;
    @FXML private Label labelInputSize;
    @FXML private Label labelOutputSize;
    @FXML private Label labelKernelSize;
    @FXML private Label labelStrideSize;
    @FXML private Label labelPaddingSize;

    @FXML private TextField textFieldInputSizeX;
    @FXML private TextField textFieldInputSizeY;
    @FXML private TextField textFieldOutputSizeX;
    @FXML private TextField textFieldOutputSizeY;

    @FXML private TextField textFieldKernelSize;
    @FXML private TextField textFieldStrideSize;
    @FXML private TextField textFieldPaddingSize;

    public ComponentConvolution1DParamController(Layer layer) {
        super(layer);
    }

    @Override
    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/design/component/convolution1d_param.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        TextFieldUtil.applyPositiveIntegerFilter(textFieldInputSizeX, AppConstant.DEFAULT_INPUT_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldKernelSize, AppConstant.DEFAULT_KERNEL_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldStrideSize, AppConstant.DEFAULT_STRIDE_SIZE);
        TextFieldUtil.applyPositiveWithZeroIntegerFilter(textFieldPaddingSize, AppConstant.DEFAULT_PADDING_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldInputSizeY, AppConstant.DEFAULT_CHANNEL_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldOutputSizeY, AppConstant.DEFAULT_CHANNEL_SIZE);
        setTextFieldByLayerProperties();

        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.title"));
        labelInputOutputX.setText(AppPropertiesSingleton.getInstance().get("frame.component.width"));
        labelInputOutputY.setText(AppPropertiesSingleton.getInstance().get("frame.component.channel"));
        labelInputSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.inputSize"));
        labelOutputSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.outputSize"));
        labelKernelSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.convolution.kernelSize"));
        labelStrideSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.convolution.strideSize"));
        labelPaddingSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.convolution.paddingSize"));
    }

    @Override
    protected void setTextFieldByLayerProperties(){
        detachTextChangedListener(textFieldInputSizeX, textFieldKernelSize, textFieldStrideSize, textFieldPaddingSize, textFieldInputSizeY, textFieldOutputSizeY);
        textFieldInputSizeX.setText(String.valueOf(layer.getProperties().getInputSizeX()));
        textFieldInputSizeY.setText(String.valueOf(layer.getProperties().getInputSizeY()));
        textFieldOutputSizeX.setText(String.valueOf(layer.getProperties().getOutputSizeX()));
        textFieldOutputSizeY.setText(String.valueOf(layer.getProperties().getOutputSizeY()));
        textFieldKernelSize.setText(String.valueOf(layer.getProperties().getKernelSize()[0]));
        textFieldStrideSize.setText(String.valueOf(layer.getProperties().getStrideSize()[0]));
        textFieldPaddingSize.setText(String.valueOf(layer.getProperties().getPaddingSize()[0]));
        attachTextChangedListener(textFieldInputSizeX, textFieldKernelSize, textFieldStrideSize, textFieldPaddingSize, textFieldInputSizeY, textFieldOutputSizeY);
    }

    @Override
    protected void textFieldChangedHandler(){
        int[] outputSize = getOutputSize();
        if(outputSize[0] <= 0){
            setTextFieldByLayerProperties();
            throw new ConvolutionOutputIsNegativeException(outputSize);
        }

        layer.getProperties().setInputSize(TextFieldUtil.parseInteger(textFieldInputSizeX), TextFieldUtil.parseInteger(textFieldInputSizeY));
        layer.getProperties().setKernelSize(new int[]{TextFieldUtil.parseInteger(textFieldKernelSize)});
        layer.getProperties().setStrideSize(new int[]{TextFieldUtil.parseInteger(textFieldStrideSize)});
        layer.getProperties().setPaddingSize(new int[]{TextFieldUtil.parseInteger(textFieldPaddingSize)});
        layer.getProperties().setOutputSize(outputSize[0], TextFieldUtil.parseInteger(textFieldOutputSizeY));
        textFieldOutputSizeX.setText(String.valueOf(layer.getProperties().getOutputSizeX()));
        notifyLayerDataChanged();
    }

    @Override
    protected int[] getOutputSize(){
        return new int[]{
                ConvolutionCalculatorUtil.getConv1DOutputSize(
                        TextFieldUtil.parseInteger(textFieldInputSizeX),
                        TextFieldUtil.parseInteger(textFieldKernelSize),
                        TextFieldUtil.parseInteger(textFieldStrideSize),
                        TextFieldUtil.parseInteger(textFieldPaddingSize))};
    }
}
