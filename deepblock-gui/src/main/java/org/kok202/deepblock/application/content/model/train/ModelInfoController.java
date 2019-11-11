package org.kok202.deepblock.application.content.model.train;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.kok202.deepblock.ai.interfaces.AIInterface;


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
        textFieldModelName.textProperty().addListener(changeListener -> modelNameChangedHandler());
        buttonInitialize.setOnAction(event -> buttonInitializeActionHandler());
    }

    public void refreshModelInfoProperty(){
        textFieldModelName.setText(AIInterface.getModelName());
        textFieldEpochNumber.setText(AIInterface.getModelLearnedEpochNumber() + "");
    }

    private void modelNameChangedHandler() {
        AIInterface.setModelName(textFieldModelName.getText());
    }

    private void buttonInitializeActionHandler(){

    }
}
