package org.kok202.deepblock.application.content.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.application.Util.TextFieldUtil;
import org.kok202.deepblock.application.facade.AppConstant;

public class ComponentReshapeParamController extends AbstractLayerComponentController {

    @FXML private Label labelWidth;
    @FXML private Label labelHeight;
    @FXML private Label labelInput;
    @FXML private Label labelOutput;

    @FXML private TextField textFieldInputX;
    @FXML private TextField textFieldInputY;
    @FXML private TextField textFieldOutputX;
    @FXML private TextField textFieldOutputY;

    public ComponentReshapeParamController(Layer layer) {
        super(layer);
    }

    @Override
    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/component/reshape_param.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        TextFieldUtil.applyPositiveIntegerFilter(textFieldInputX, AppConstant.DEFAULT_INPUT_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldInputY, AppConstant.DEFAULT_INPUT_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldOutputX, AppConstant.DEFAULT_OUTPUT_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldOutputY, AppConstant.DEFAULT_OUTPUT_SIZE);
        setTextFieldByLayerProperties();
    }

    private void setTextFieldByLayerProperties(){
        detachTextChangedListener(textFieldInputX, textFieldInputY, textFieldOutputX, textFieldOutputY);
        textFieldInputX.setText(String.valueOf(layer.getProperties().getInputSize()[0]));
        textFieldInputY.setText(String.valueOf(layer.getProperties().getInputSize()[1]));
        textFieldOutputX.setText(String.valueOf(layer.getProperties().getOutputSize()[0]));
        textFieldOutputY.setText(String.valueOf(layer.getProperties().getOutputSize()[1]));
        attachTextChangedListener(textFieldInputX, textFieldInputY, textFieldOutputX, textFieldOutputY);
    }

    @Override
    protected void textFieldChangedHandler(){
        layer.getProperties().setInputSize(
                TextFieldUtil.parseInteger(textFieldInputX),
                TextFieldUtil.parseInteger(textFieldInputY));
        layer.getProperties().setOutputSize(
                TextFieldUtil.parseInteger(textFieldOutputX),
                TextFieldUtil.parseInteger(textFieldOutputY));
        notifyLayerDataChanged();
    }
}
