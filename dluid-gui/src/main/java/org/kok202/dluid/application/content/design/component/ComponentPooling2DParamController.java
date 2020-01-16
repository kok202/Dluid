package org.kok202.dluid.application.content.design.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.deeplearning4j.nn.conf.layers.PoolingType;
import org.kok202.dluid.AppConstant;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.util.ConvolutionCalculatorUtil;
import org.kok202.dluid.application.adapter.MenuAdapter;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;
import org.kok202.dluid.application.util.TextFieldUtil;
import org.kok202.dluid.domain.exception.ConvolutionOutputIsNegativeException;

public class ComponentPooling2DParamController extends AbstractConvolutionLayerComponentController {

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
    @FXML private Label labelPoolingType;

    @FXML private TextField textFieldInputSizeX;
    @FXML private TextField textFieldInputSizeY;
    @FXML private TextField textFieldInputSizeZ;
    @FXML private TextField textFieldOutputSizeX;
    @FXML private TextField textFieldOutputSizeY;
    @FXML private TextField textFieldOutputSizeZ;
    @FXML private TextField textFieldKernelSizeX;
    @FXML private TextField textFieldKernelSizeY;
    @FXML private TextField textFieldStrideSizeX;
    @FXML private TextField textFieldStrideSizeY;
    @FXML private TextField textFieldPaddingSizeX;
    @FXML private TextField textFieldPaddingSizeY;
    @FXML private MenuButton menuButtonPoolingType;

    public ComponentPooling2DParamController(Layer layer) {
        super(layer);
    }

    @Override
    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/design/component/pooling2d_param.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        TextFieldUtil.applyPositiveIntegerFilter(textFieldInputSizeX, AppConstant.DEFAULT_INPUT_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldInputSizeY, AppConstant.DEFAULT_INPUT_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldInputSizeZ, AppConstant.DEFAULT_INPUT_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldOutputSizeX, AppConstant.DEFAULT_INPUT_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldOutputSizeY, AppConstant.DEFAULT_INPUT_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldOutputSizeZ, AppConstant.DEFAULT_INPUT_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldInputSizeX, AppConstant.DEFAULT_INPUT_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldInputSizeY, AppConstant.DEFAULT_INPUT_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldKernelSizeX, AppConstant.DEFAULT_KERNEL_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldKernelSizeY, AppConstant.DEFAULT_KERNEL_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldStrideSizeX, AppConstant.DEFAULT_STRIDE_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldStrideSizeY, AppConstant.DEFAULT_STRIDE_SIZE);
        TextFieldUtil.applyPositiveWithZeroIntegerFilter(textFieldPaddingSizeX, AppConstant.DEFAULT_PADDING_SIZE);
        TextFieldUtil.applyPositiveWithZeroIntegerFilter(textFieldPaddingSizeY, AppConstant.DEFAULT_PADDING_SIZE);
        setTextFieldByLayerProperties();
        initializeMenuButtonPoolingType();

        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.title"));
        labelInputOutputWidth.setText(AppPropertiesSingleton.getInstance().get("frame.component.width"));
        labelInputOutputHeight.setText(AppPropertiesSingleton.getInstance().get("frame.component.height"));
        labelInputOutputChannel.setText(AppPropertiesSingleton.getInstance().get("frame.component.channel"));
        labelWidth.setText(AppPropertiesSingleton.getInstance().get("frame.component.width"));
        labelHeight.setText(AppPropertiesSingleton.getInstance().get("frame.component.height"));
        labelInputSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.inputSize"));
        labelOutputSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.outputSize"));
        labelKernelSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.pooling.kernelSize"));
        labelStrideSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.pooling.strideSize"));
        labelPaddingSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.pooling.paddingSize"));
        labelPoolingType.setText(AppPropertiesSingleton.getInstance().get("frame.component.pooling.type"));
    }

    @Override
    protected void setTextFieldByLayerProperties(){
        detachTextChangedListener(
                textFieldInputSizeX, textFieldKernelSizeX, textFieldStrideSizeX, textFieldPaddingSizeX,
                textFieldInputSizeY, textFieldKernelSizeY, textFieldStrideSizeY, textFieldPaddingSizeY);
        textFieldInputSizeX.setText(String.valueOf(layer.getProperties().getInputSizeX()));
        textFieldInputSizeY.setText(String.valueOf(layer.getProperties().getInputSizeY()));
        textFieldInputSizeZ.setText(String.valueOf(layer.getProperties().getInputSizeZ()));
        textFieldOutputSizeX.setText(String.valueOf(layer.getProperties().getOutputSizeX()));
        textFieldOutputSizeY.setText(String.valueOf(layer.getProperties().getOutputSizeY()));
        textFieldOutputSizeZ.setText(String.valueOf(layer.getProperties().getOutputSizeZ()));
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

    private void initializeMenuButtonPoolingType(){
        MenuAdapter<PoolingType> menuAdapter = new MenuAdapter<>(menuButtonPoolingType);
        menuAdapter.setMenuItemChangedListener(poolingType -> {
            layer.getProperties().setPoolingType(poolingType);
            notifyLayerDataChanged();
        });
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.pooling.type.max"), PoolingType.MAX);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.pooling.type.sum"), PoolingType.SUM);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.pooling.type.avg"), PoolingType.AVG);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.pooling.type.pnorm"), PoolingType.PNORM);
        menuAdapter.setDefaultMenuItem(layer.getProperties().getPoolingType());
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
                TextFieldUtil.parseInteger(textFieldInputSizeZ));
        layer.getProperties().setKernelSize(new int[]{
                TextFieldUtil.parseInteger(textFieldKernelSizeX),
                TextFieldUtil.parseInteger(textFieldKernelSizeY)});
        layer.getProperties().setStrideSize(new int[]{
                TextFieldUtil.parseInteger(textFieldStrideSizeX),
                TextFieldUtil.parseInteger(textFieldStrideSizeY)});
        layer.getProperties().setPaddingSize(new int[]{
                TextFieldUtil.parseInteger(textFieldPaddingSizeX),
                TextFieldUtil.parseInteger(textFieldPaddingSizeY)});

        layer.getProperties().setOutputSize(outputSize[0], outputSize[1], TextFieldUtil.parseInteger(textFieldInputSizeZ));
        textFieldOutputSizeX.setText(String.valueOf(layer.getProperties().getOutputSizeX()));
        textFieldOutputSizeY.setText(String.valueOf(layer.getProperties().getOutputSizeY()));
        textFieldOutputSizeZ.setText(String.valueOf(layer.getProperties().getOutputSizeX()));
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
