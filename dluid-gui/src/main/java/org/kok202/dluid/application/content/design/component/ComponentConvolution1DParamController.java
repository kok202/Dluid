package org.kok202.dluid.application.content.design.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.kok202.dluid.AppConstant;
import org.kok202.dluid.CanvasConstant;
import org.kok202.dluid.CanvasFacade;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.util.ConvolutionCalculatorUtil;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;
import org.kok202.dluid.application.util.TextFieldUtil;
import org.kok202.dluid.domain.exception.ConvolutionOutputIsNegativeException;

public class ComponentConvolution1DParamController extends AbstractConvolutionLayerComponentController {

    @FXML private Label labelInputSize;
    @FXML private Label labelOutputSize;
    @FXML private Label labelKernelSize;
    @FXML private Label labelStrideSize;
    @FXML private Label labelPaddingSize;
    @FXML private Label labelInputChannelSize;
    @FXML private Label labelOutputChannelSize;

    @FXML private TextField textFieldInputSize;
    @FXML private TextField textFieldKernelSize;
    @FXML private TextField textFieldStrideSize;
    @FXML private TextField textFieldPaddingSize;
    @FXML private TextField textFieldOutputSize;
    @FXML private TextField textFieldInputChannelSize;
    @FXML private TextField textFieldOutputChannelSize;

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
        TextFieldUtil.applyPositiveIntegerFilter(textFieldInputSize, AppConstant.DEFAULT_INPUT_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldKernelSize, AppConstant.DEFAULT_KERNEL_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldStrideSize, AppConstant.DEFAULT_STRIDE_SIZE);
        TextFieldUtil.applyPositiveWithZeroIntegerFilter(textFieldPaddingSize, AppConstant.DEFAULT_PADDING_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldInputChannelSize, AppConstant.DEFAULT_CHANNEL_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldOutputChannelSize, AppConstant.DEFAULT_CHANNEL_SIZE);
        setTextFieldByLayerProperties();

        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.title"));
        labelInputSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.inputSize"));
        labelOutputSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.outputSize"));
        labelKernelSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.convolution.kernelSize"));
        labelStrideSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.convolution.strideSize"));
        labelPaddingSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.convolution.paddingSize"));
        labelInputChannelSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.convolution.inputChannelSize"));
        labelOutputChannelSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.convolution.outputChannelSize"));
    }

    @Override
    protected void setTextFieldByLayerProperties(){
        detachTextChangedListener(textFieldInputSize, textFieldKernelSize, textFieldStrideSize, textFieldPaddingSize,
                textFieldInputChannelSize, textFieldOutputChannelSize);
        textFieldInputSize.setText(String.valueOf(layer.getProperties().getInputSize()[0]));
        textFieldOutputSize.setText(String.valueOf(layer.getProperties().getOutputSize()[0]));
        textFieldKernelSize.setText(String.valueOf(layer.getProperties().getKernelSize()[0]));
        textFieldStrideSize.setText(String.valueOf(layer.getProperties().getStrideSize()[0]));
        textFieldPaddingSize.setText(String.valueOf(layer.getProperties().getPaddingSize()[0]));
        textFieldInputChannelSize.setText(String.valueOf(layer.getProperties().getInputChannelSize()));
        textFieldOutputChannelSize.setText(String.valueOf(layer.getProperties().getOutputChannelSize()));
        attachTextChangedListener(textFieldInputSize, textFieldKernelSize, textFieldStrideSize, textFieldPaddingSize,
                textFieldInputChannelSize, textFieldOutputChannelSize);
    }

    @Override
    protected void textFieldChangedHandler(){
        int[] outputSize = getOutputSize();
        if(outputSize[0] <= 0){
            setTextFieldByLayerProperties();
            throw new ConvolutionOutputIsNegativeException(outputSize);
        }

        layer.getProperties().setInputSize(TextFieldUtil.parseInteger(textFieldInputSize));
        layer.getProperties().setKernelSize(new int[]{TextFieldUtil.parseInteger(textFieldKernelSize)});
        layer.getProperties().setStrideSize(new int[]{TextFieldUtil.parseInteger(textFieldStrideSize)});
        layer.getProperties().setPaddingSize(new int[]{TextFieldUtil.parseInteger(textFieldPaddingSize)});
        layer.getProperties().setInputChannelSize(TextFieldUtil.parseInteger(textFieldInputChannelSize));
        layer.getProperties().setOutputChannelSize(TextFieldUtil.parseInteger(textFieldOutputChannelSize));

        textFieldOutputSize.setText(String.valueOf(outputSize[0]));
        layer.getProperties().setOutputSize(outputSize[0]);
        notifyLayerDataChanged();
    }

    @Override
    protected int[] getOutputSize(){
        return new int[]{
                ConvolutionCalculatorUtil.getConv1DOutputSize(
                        TextFieldUtil.parseInteger(textFieldInputSize),
                        TextFieldUtil.parseInteger(textFieldKernelSize),
                        TextFieldUtil.parseInteger(textFieldStrideSize),
                        TextFieldUtil.parseInteger(textFieldPaddingSize))};
    }

    @Override
    protected void notifyLayerDataChanged(){
        double height = layer.getProperties().getOutputChannelSize() * CanvasConstant.NODE_DEFAULT_HEIGHT;
        CanvasFacade.findGraphNodeByLayerId(layer.getId()).getData().getBlockInfo().setHeight(height);
        CanvasFacade.notifyLayerDataChanged(layer.getId());
    }
}
