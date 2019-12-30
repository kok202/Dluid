package org.kok202.dluid.application.content.design.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.kok202.dluid.CanvasFacade;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;
import org.kok202.dluid.application.util.MathUtil;
import org.kok202.dluid.application.util.TextFieldUtil;
import org.kok202.dluid.canvas.entity.InputBlockProperty;
import org.kok202.dluid.domain.exception.MultiOutputLayerException;

import java.util.List;

public class ComponentInputParamController extends AbstractLayerComponentController {

    @FXML private Label labelIsStartOfTest;
    @FXML private Label labelInputSize;
    @FXML private Label labelOutputSize;
    @FXML private Label labelWidth;
    @FXML private Label labelHeight;

    @FXML private CheckBox checkBoxIsStartOfTest;
    @FXML private TextField textFieldInputSize;
    @FXML private TextField textFieldOutputSizeX;
    @FXML private TextField textFieldOutputSizeY;
    @FXML private Button buttonOutputSizeChangeUp;
    @FXML private Button buttonOutputSizeChangeDown;

    public ComponentInputParamController(Layer layer) {
        super(layer);
    }

    @Override
    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/design/component/input_param.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        checkBoxIsStartOfTest.selectedProperty().addListener((selected, oldValue, newValue) -> {
            if(newValue == true){
                if(CanvasFacade.findTestInputLayer().isPresent() &&
                    CanvasFacade.findTestInputLayer().get().getData().getBlockLayer().getId() != layer.getId()){
                    checkBoxIsStartOfTest.setSelected(false);
                    throw new MultiOutputLayerException();
                }
            }
            layer.getProperties().setTestInput(newValue);
        });
        buttonOutputSizeChangeUp.setOnAction(actionEvent -> {
            InputBlockProperty inputBlockProperty = (InputBlockProperty) CanvasFacade.findGraphNodeByLayerId(layer.getId()).getData().getBlockInfo().getExtra();
            inputBlockProperty.setPointingIndex(inputBlockProperty.getPointingIndex() + 1);
            refreshOutputSize();
        });
        buttonOutputSizeChangeDown.setOnAction(actionEvent -> {
            InputBlockProperty inputBlockProperty = (InputBlockProperty) CanvasFacade.findGraphNodeByLayerId(layer.getId()).getData().getBlockInfo().getExtra();
            inputBlockProperty.setPointingIndex(inputBlockProperty.getPointingIndex() - 1);
            refreshOutputSize();
        });
        TextFieldUtil.applyPositiveIntegerFilter(textFieldInputSize, 1);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldOutputSizeX, 1);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldOutputSizeY, 1);
        setTextFieldByLayerProperties();
    }

    protected void setTextFieldByLayerProperties(){
        textFieldInputSize.setText(String.valueOf(layer.getProperties().getInputSize()[0]));
        textFieldOutputSizeX.setText(String.valueOf(layer.getProperties().getOutputSize()[0]));
        textFieldOutputSizeY.setText(String.valueOf(layer.getProperties().getOutputSize()[1]));
        checkBoxIsStartOfTest.setSelected(layer.getProperties().isTestInput());
        attachTextChangedListener(textFieldInputSize);

        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.title"));
        labelWidth.setText(AppPropertiesSingleton.getInstance().get("frame.component.2d.width"));
        labelHeight.setText(AppPropertiesSingleton.getInstance().get("frame.component.2d.height"));
        labelIsStartOfTest.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.isStartOfTest"));
        labelInputSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.inputSize"));
        labelOutputSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.outputSize"));
    }

    @Override
    protected void textFieldChangedHandler(){
        changeInputSize();
        refreshOutputSize();
    }

    private void changeInputSize(){
        int x = TextFieldUtil.parseInteger(textFieldInputSize, 1);
        layer.getProperties().setInputSize(x);
    }

    private void refreshOutputSize(){
        int inputSize = Math.max(layer.getProperties().getInputSize()[0] * layer.getProperties().getInputSize()[1], 1);
        List<Integer> recommendedDivisors = MathUtil.getDivisors(inputSize);
        InputBlockProperty inputBlockProperty = (InputBlockProperty) CanvasFacade.findGraphNodeByLayerId(layer.getId()).getData().getBlockInfo().getExtra();
        int outputSizeY = recommendedDivisors.get(inputBlockProperty.getPointingIndex(recommendedDivisors.size()));
        int outputSizeX = inputSize / outputSizeY;
        layer.getProperties().setOutputSize(outputSizeX, outputSizeY);
        textFieldOutputSizeX.setText(String.valueOf(outputSizeX));
        textFieldOutputSizeY.setText(String.valueOf(outputSizeY));
        notifyLayerDataChanged();
    }
}
