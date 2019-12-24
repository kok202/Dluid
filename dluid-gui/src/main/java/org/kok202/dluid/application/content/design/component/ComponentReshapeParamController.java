package org.kok202.dluid.application.content.design.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.kok202.dluid.AppConstant;
import org.kok202.dluid.CanvasFacade;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;
import org.kok202.dluid.application.util.MathUtil;
import org.kok202.dluid.application.util.TextFieldUtil;
import org.kok202.dluid.canvas.entity.ReshapeBlockProperty;

import java.util.List;

public class ComponentReshapeParamController extends AbstractLayerComponentController {

    @FXML private Label labelWidth;
    @FXML private Label labelHeight;
    @FXML private Label labelInput;
    @FXML private Label labelOutput;

    @FXML private TextField textFieldInputSizeX;
    @FXML private TextField textFieldInputSizeY;
    @FXML private TextField textFieldOutputSizeX;
    @FXML private TextField textFieldOutputSizeY;
    @FXML private Button buttonOutputSizeChangeUp;
    @FXML private Button buttonOutputSizeChangeDown;

    public ComponentReshapeParamController(Layer layer) {
        super(layer);
    }

    @Override
    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/design/component/reshape_param.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        buttonOutputSizeChangeUp.setOnAction(actionEvent -> {
            ReshapeBlockProperty reshapeBlockProperty = (ReshapeBlockProperty) CanvasFacade.findGraphNodeByLayerId(layer.getId()).getData().getBlockInfo().getExtra();
            reshapeBlockProperty.setPointingIndex(reshapeBlockProperty.getPointingIndex() + 1);
            refreshInputOutputSize();
        });
        buttonOutputSizeChangeDown.setOnAction(actionEvent -> {
            ReshapeBlockProperty reshapeBlockProperty = (ReshapeBlockProperty) CanvasFacade.findGraphNodeByLayerId(layer.getId()).getData().getBlockInfo().getExtra();
            reshapeBlockProperty.setPointingIndex(reshapeBlockProperty.getPointingIndex() - 1);
            refreshInputOutputSize();
        });
        TextFieldUtil.applyPositiveIntegerFilter(textFieldInputSizeX, AppConstant.DEFAULT_INPUT_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldInputSizeY, AppConstant.DEFAULT_INPUT_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldOutputSizeX, AppConstant.DEFAULT_OUTPUT_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldOutputSizeY, AppConstant.DEFAULT_OUTPUT_SIZE);
        setTextFieldByLayerProperties();

        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.title"));
        labelWidth.setText(AppPropertiesSingleton.getInstance().get("frame.component.2d.width"));
        labelHeight.setText(AppPropertiesSingleton.getInstance().get("frame.component.2d.height"));
        labelInput.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.inputSize"));
        labelOutput.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.outputSize"));
    }

    private void setTextFieldByLayerProperties(){
        detachTextChangedListener(textFieldInputSizeX, textFieldInputSizeY);
        textFieldInputSizeX.setText(String.valueOf(layer.getProperties().getInputSize()[0]));
        textFieldInputSizeY.setText(String.valueOf(layer.getProperties().getInputSize()[1]));
        textFieldOutputSizeX.setText(String.valueOf(layer.getProperties().getOutputSize()[0]));
        textFieldOutputSizeY.setText(String.valueOf(layer.getProperties().getOutputSize()[1]));
        attachTextChangedListener(textFieldInputSizeX, textFieldInputSizeY);
    }

    @Override
    protected void textFieldChangedHandler(){
        layer.getProperties().setInputSize(
                TextFieldUtil.parseInteger(textFieldInputSizeX),
                TextFieldUtil.parseInteger(textFieldInputSizeY));
        refreshInputOutputSize();
    }

    private void refreshInputOutputSize(){
        int inputSize = Math.max(layer.getProperties().getInputSize()[0] * layer.getProperties().getInputSize()[1], 1);
        List<Integer> recommendedDivisors = MathUtil.getDivisors(inputSize);
        ReshapeBlockProperty reshapeBlockProperty = (ReshapeBlockProperty) CanvasFacade.findGraphNodeByLayerId(layer.getId()).getData().getBlockInfo().getExtra();
        int outputSizeY = recommendedDivisors.get(reshapeBlockProperty.getPointingIndex(recommendedDivisors.size()));
        int outputSizeX = inputSize / outputSizeY;
        layer.getProperties().setOutputSize(outputSizeX, outputSizeY);
        textFieldOutputSizeX.setText(String.valueOf(outputSizeX));
        textFieldOutputSizeY.setText(String.valueOf(outputSizeY));
        notifyLayerDataChanged();
    }
}
