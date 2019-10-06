package org.kok202.deepblock.application.content.model.train;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.IntegerStringConverter;
import org.kok202.deepblock.ai.global.AIPropertiesSingleton;
import org.kok202.deepblock.application.Util.TextFieldUtil;


public class ModelInfoController extends AbstractModelTrainController {
    @FXML private Label labelModelName;
    @FXML private Label labelEpochNumber;
    @FXML private TextField textFieldModelName;
    @FXML private TextField textFieldEpochNumber;
    @FXML private Button buttonInitialize;

    public AnchorPane createView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/model/train/info.fxml"));
        fxmlLoader.setController(this);
        AnchorPane content = fxmlLoader.load();
        return content;
    }
    @Override
    protected void initialize() throws Exception {
        TextFieldUtil.applyPositiveIntegerFilter(textFieldEpochNumber, 0);
        textFieldModelName.textProperty().addListener(changeListener -> textFieldChangeHandler());
        textFieldEpochNumber.textProperty().addListener(changeListener -> textFieldChangeHandler());
        textFieldEpochNumber.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        buttonInitialize.setOnAction(event -> buttonInitializeActionHandler());
    }

    private void textFieldChangeHandler(){
        setModelName();
        setTrainedEpochNumber();
    }

    private void setModelName() {
        String value = textFieldModelName.getText();
        AIPropertiesSingleton.getInstance().getModelSettingProperty().setModelName(value);
    }

    private void setTrainedEpochNumber() {
        int value = TextFieldUtil.parseInteger(textFieldEpochNumber, 0);
        AIPropertiesSingleton.getInstance().getModelSettingProperty().setModelLearnedEpochNumber(value);
    }

    private void buttonInitializeActionHandler(){

    }
}
