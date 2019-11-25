package org.kok202.dluid.application.content.model;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.kok202.dluid.application.common.AbstractController;
import org.kok202.dluid.application.content.model.train.ModelInfoController;
import org.kok202.dluid.application.content.model.train.ModelTrainParamController;
import org.kok202.dluid.application.content.model.train.ModelTrainTaskController;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;


public class TabModelTrainController extends AbstractController {
    @FXML private VBox content;
    @FXML private VBox vBoxForModelInfo;
    @FXML private VBox vBoxForTrainData;
    @FXML private VBox vBoxForTrainTask;
    @FXML private Label labelTrainModelInfo;
    @FXML private Label labelTrainDataSetting;
    @FXML private Label labelTrainTask;

    private ModelInfoController modelInfoController;
    private ModelTrainParamController modelTrainParamController;
    private ModelTrainTaskController modelTrainTaskController;

    public Tab createView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/model/tab_model_train.fxml"));
        fxmlLoader.setController(this);
        AnchorPane content = fxmlLoader.load();

        Tab instanceTab = new Tab();
        instanceTab.setText(AppPropertiesSingleton.getInstance().get("frame.tabs.model.setting"));
        instanceTab.setContent(content);
        return instanceTab;
    }

    @Override
    protected void initialize() throws Exception {
        modelInfoController = new ModelInfoController();
        modelTrainParamController = new ModelTrainParamController();
        modelTrainTaskController = new ModelTrainTaskController();

        vBoxForModelInfo.getChildren().add(modelInfoController.createView());
        vBoxForTrainData.getChildren().add(modelTrainParamController.createView());
        vBoxForTrainTask.getChildren().add(modelTrainTaskController.createView());

        labelTrainModelInfo.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.modelInfo.label"));
        labelTrainDataSetting.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.dataSetting.label"));
        labelTrainTask.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.trainTask.label"));
    }

    public void refreshModelInfo(){
        modelInfoController.refreshModelInfoProperty();
    }
}
