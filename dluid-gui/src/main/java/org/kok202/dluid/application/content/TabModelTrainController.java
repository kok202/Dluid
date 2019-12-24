package org.kok202.dluid.application.content;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import org.kok202.dluid.application.common.AbstractController;
import org.kok202.dluid.application.content.train.ModelInformationController;
import org.kok202.dluid.application.content.train.ModelTrainFileLoaderController;
import org.kok202.dluid.application.content.train.ModelTrainParamController;
import org.kok202.dluid.application.content.train.ModelTrainTaskController;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;

public class TabModelTrainController extends AbstractController {
    @FXML private VBox content;
    @FXML private VBox vBoxForModelInfo;
    @FXML private VBox vBoxForTrainFileLoader;
    @FXML private VBox vBoxForTrainParamSetting;
    @FXML private VBox vBoxForTrainTask;
    @FXML private Label labelTrainModelInfo;
    @FXML private Label labelTrainFileLoader;
    @FXML private Label labelTrainParamSetting;
    @FXML private Label labelTrainTask;

    @Getter
    private ModelInformationController modelInformationController;
    @Getter
    private ModelTrainParamController modelTrainParamController;
    @Getter
    private ModelTrainFileLoaderController modelTrainFileLoaderController;
    @Getter
    private ModelTrainTaskController modelTrainTaskController;

    public Tab createView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/tab_model_train.fxml"));
        fxmlLoader.setController(this);
        AnchorPane content = fxmlLoader.load();

        Tab instanceTab = new Tab();
        instanceTab.setText(AppPropertiesSingleton.getInstance().get("frame.tabs.model.setting"));
        instanceTab.setContent(content);
        return instanceTab;
    }

    @Override
    protected void initialize() throws Exception {
        modelInformationController = new ModelInformationController();
        modelTrainFileLoaderController = new ModelTrainFileLoaderController();
        modelTrainParamController = new ModelTrainParamController();
        modelTrainTaskController = new ModelTrainTaskController();

        vBoxForModelInfo.getChildren().add(modelInformationController.createView());
        vBoxForTrainFileLoader.getChildren().add(modelTrainFileLoaderController.createView());
        vBoxForTrainParamSetting.getChildren().add(modelTrainParamController.createView());
        vBoxForTrainTask.getChildren().add(modelTrainTaskController.createView());

        labelTrainModelInfo.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.modelInfo.label"));
        labelTrainFileLoader.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.fileLoader.label"));
        labelTrainParamSetting.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.paramSetting.label"));
        labelTrainTask.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.trainTask.label"));
        setSettingExpandAndDisable(true);
    }

    public void setSettingExpandAndDisable(boolean disable){
        modelTrainFileLoaderController.getTitledPane().setDisable(disable);
        modelTrainFileLoaderController.getTitledPane().setExpanded(!disable);
        modelTrainParamController.getTitledPane().setDisable(disable);
        modelTrainParamController.getTitledPane().setExpanded(!disable);
        modelTrainTaskController.getTitledPane().setDisable(disable);
        modelTrainTaskController.getTitledPane().setExpanded(!disable);
    }
}
