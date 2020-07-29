package org.kok202.dluid.content;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import org.kok202.dluid.AppFacade;
import org.kok202.dluid.common.AbstractController;
import org.kok202.dluid.content.train.ModelInformationController;
import org.kok202.dluid.content.train.ModelTrainFileLoaderController;
import org.kok202.dluid.content.train.ModelTrainTaskController;
import org.kok202.dluid.reducer.RefreshTrainingButtonDisableReducer;
import org.kok202.dluid.reducer.RefreshTrainingButtonEnableReducer;
import org.kok202.dluid.singleton.AppPropertiesSingleton;

public class TabModelTrainController extends AbstractController {
    @FXML private VBox content;
    @FXML private VBox vBoxForModelInfo;
    @FXML private VBox vBoxForTrainFileLoader;
    @FXML private VBox vBoxForTrainParamSetting;
    @FXML private VBox vBoxForTrainTask;
    @FXML private Label labelTrainModelInfo;
    @FXML private Label labelTrainFileLoader;
    @FXML private Label labelTrainTask;

    @Getter
    private ModelInformationController modelInformationController;
    @Getter
    private ModelTrainFileLoaderController modelTrainFileLoaderController;
    @Getter
    private ModelTrainTaskController modelTrainTaskController;

    public Tab createView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/tab_model_train.fxml"));
        fxmlLoader.setController(this);
        AnchorPane content = fxmlLoader.load();

        Tab instanceTab = new Tab();
        instanceTab.setText(AppPropertiesSingleton.getInstance().get("frame.tabs.model.train"));
        instanceTab.setContent(content);
        return instanceTab;
    }

    @Override
    protected void initialize() throws Exception {
        modelInformationController = new ModelInformationController();
        modelTrainFileLoaderController = new ModelTrainFileLoaderController();
        modelTrainTaskController = new ModelTrainTaskController();

        vBoxForModelInfo.getChildren().add(modelInformationController.createView());
        vBoxForTrainFileLoader.getChildren().add(modelTrainFileLoaderController.createView());
        vBoxForTrainTask.getChildren().add(modelTrainTaskController.createView());

        labelTrainModelInfo.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.modelInfo.label"));
        labelTrainFileLoader.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.fileLoader.label"));
        labelTrainTask.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.trainTask.label"));
        setSettingExpandAndDisable(true);
        AppFacade.addReducer(new RefreshTrainingButtonEnableReducer(this));
        AppFacade.addReducer(new RefreshTrainingButtonDisableReducer(this));
    }

    public void setSettingExpandAndDisable(boolean disable){
        modelTrainFileLoaderController.getTitledPane().setDisable(disable);
        modelTrainFileLoaderController.getTitledPane().setExpanded(!disable);
        modelTrainTaskController.getTitledPane().setDisable(disable);
        modelTrainTaskController.getTitledPane().setExpanded(!disable);
    }
}
