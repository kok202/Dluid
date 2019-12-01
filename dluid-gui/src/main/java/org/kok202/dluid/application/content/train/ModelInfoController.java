package org.kok202.dluid.application.content.train;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import org.kok202.dluid.ai.AIFacade;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;


public class ModelInfoController extends AbstractModelTrainController {
    @FXML private TitledPane titledPane;
    @FXML private Label labelModelName;
    @FXML private Label labelEpochNumber;
    @FXML private TextField textFieldModelName;
    @FXML private TextField textFieldEpochNumber;
    @FXML private Button buttonInitialize;

    public AnchorPane createView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/train/info.fxml"));
        fxmlLoader.setController(this);
        AnchorPane content = fxmlLoader.load();
        return content;
    }

    @Override
    protected void initialize() throws Exception {
        textFieldModelName.textProperty().addListener(changeListener -> modelNameChangedHandler());
        buttonInitialize.setOnAction(event -> buttonInitializeActionHandler());
        titledPane.setExpanded(false);

        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.modelInfo.title"));
        labelModelName.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.modelInfo.name"));
        labelEpochNumber.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.modelInfo.totalEpoch"));
        buttonInitialize.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.modelInfo.initialize"));
    }

    public void refreshModelInfoProperty(){
        textFieldModelName.setText(AIFacade.getModelName());
        textFieldEpochNumber.setText(AIFacade.getModelLearnedEpochNumber() + "");
    }

    private void modelNameChangedHandler() {
        AIFacade.setModelName(textFieldModelName.getText());
    }

    private void buttonInitializeActionHandler(){

    }
}
