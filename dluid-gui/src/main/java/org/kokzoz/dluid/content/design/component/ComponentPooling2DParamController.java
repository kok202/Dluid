package org.kokzoz.dluid.content.design.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.kokzoz.dluid.AppConstant;
import org.kokzoz.dluid.adapter.MenuAdapter;
import org.kokzoz.dluid.ai.util.ConvolutionCalculatorUtil;
import org.kokzoz.dluid.domain.entity.Layer;
import org.kokzoz.dluid.domain.entity.enumerator.PoolingTypeWrapper;
import org.kokzoz.dluid.domain.exception.ConvolutionOutputIsNegativeException;
import org.kokzoz.dluid.singleton.AppPropertiesSingleton;
import org.kokzoz.dluid.util.TextFieldUtil;

public class ComponentPooling2DParamController extends AbstractConvolutionLayerComponentController {

    @FXML private Label labelInputOutputX;
    @FXML private Label labelInputOutputY;
    @FXML private Label labelInputOutputZ;
    @FXML private Label labelInputSize;
    @FXML private Label labelOutputSize;

    @FXML private Label labelX;
    @FXML private Label labelY;
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
        labelInputOutputX.setText(AppPropertiesSingleton.getInstance().get("frame.component.width"));
        labelInputOutputY.setText(AppPropertiesSingleton.getInstance().get("frame.component.height"));
        labelInputOutputZ.setText(AppPropertiesSingleton.getInstance().get("frame.component.channel"));
        labelX.setText(AppPropertiesSingleton.getInstance().get("frame.component.width"));
        labelY.setText(AppPropertiesSingleton.getInstance().get("frame.component.height"));
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
                textFieldInputSizeY, textFieldKernelSizeY, textFieldStrideSizeY, textFieldPaddingSizeY,
                textFieldInputSizeZ);
        textFieldInputSizeX.setText(String.valueOf(layer.getProperties().getInput().getX()));
        textFieldInputSizeY.setText(String.valueOf(layer.getProperties().getInput().getY()));
        textFieldInputSizeZ.setText(String.valueOf(layer.getProperties().getInput().getChannel()));
        textFieldOutputSizeX.setText(String.valueOf(layer.getProperties().getOutput().getX()));
        textFieldOutputSizeY.setText(String.valueOf(layer.getProperties().getOutput().getY()));
        textFieldOutputSizeZ.setText(String.valueOf(layer.getProperties().getOutput().getChannel()));
        textFieldStrideSizeX.setText(String.valueOf(layer.getProperties().getStrideSize()[0]));
        textFieldStrideSizeY.setText(String.valueOf(layer.getProperties().getStrideSize()[1]));
        textFieldPaddingSizeX.setText(String.valueOf(layer.getProperties().getPaddingSize()[0]));
        textFieldPaddingSizeY.setText(String.valueOf(layer.getProperties().getPaddingSize()[1]));
        textFieldKernelSizeX.setText(String.valueOf(layer.getProperties().getKernelSize()[0]));
        textFieldKernelSizeY.setText(String.valueOf(layer.getProperties().getKernelSize()[1]));
        attachTextChangedListener(
                textFieldInputSizeX, textFieldKernelSizeX, textFieldStrideSizeX, textFieldPaddingSizeX,
                textFieldInputSizeY, textFieldKernelSizeY, textFieldStrideSizeY, textFieldPaddingSizeY,
                textFieldInputSizeZ);
    }

    private void initializeMenuButtonPoolingType(){
        MenuAdapter<PoolingTypeWrapper> menuAdapter = new MenuAdapter<>(menuButtonPoolingType);
        menuAdapter.setMenuItemChangedListener(poolingType -> {
            layer.getProperties().setPoolingType(poolingType);
            reshapeBlock();
        });
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.pooling.type.max"), PoolingTypeWrapper.MAX);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.component.pooling.type.avg"), PoolingTypeWrapper.AVG);
        menuAdapter.setDefaultMenuItem(layer.getProperties().getPoolingType());
    }

    @Override
    protected void textFieldChangedHandler(){
        int[] outputSize= getOutputSize();
        if(outputSize[0] <= 0 || outputSize[1] <= 0){
            setTextFieldByLayerProperties();
            throw new ConvolutionOutputIsNegativeException(outputSize);
        }

        layer.getProperties().getInput().setX(TextFieldUtil.parseInteger(textFieldInputSizeX));
        layer.getProperties().getInput().setY(TextFieldUtil.parseInteger(textFieldInputSizeY));
        layer.getProperties().getInput().setChannel(TextFieldUtil.parseInteger(textFieldInputSizeZ));
        layer.getProperties().setKernelSize(new int[]{
                TextFieldUtil.parseInteger(textFieldKernelSizeX),
                TextFieldUtil.parseInteger(textFieldKernelSizeY)});
        layer.getProperties().setStrideSize(new int[]{
                TextFieldUtil.parseInteger(textFieldStrideSizeX),
                TextFieldUtil.parseInteger(textFieldStrideSizeY)});
        layer.getProperties().setPaddingSize(new int[]{
                TextFieldUtil.parseInteger(textFieldPaddingSizeX),
                TextFieldUtil.parseInteger(textFieldPaddingSizeY)});

        layer.getProperties().getOutput().setX(outputSize[0]);
        layer.getProperties().getOutput().setY(outputSize[1]);
        layer.getProperties().getOutput().setChannel(TextFieldUtil.parseInteger(textFieldInputSizeZ));
        textFieldOutputSizeX.setText(String.valueOf(layer.getProperties().getOutput().getX()));
        textFieldOutputSizeY.setText(String.valueOf(layer.getProperties().getOutput().getY()));
        textFieldOutputSizeZ.setText(String.valueOf(layer.getProperties().getOutput().getChannel()));
        reshapeBlock();
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
