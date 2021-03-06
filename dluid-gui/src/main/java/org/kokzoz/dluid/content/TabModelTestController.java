package org.kokzoz.dluid.content;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Data;
import org.kokzoz.dluid.AppFacade;
import org.kokzoz.dluid.common.AbstractController;
import org.kokzoz.dluid.content.test.ModelTestFeatureController;
import org.kokzoz.dluid.content.test.ModelTestTestingController;
import org.kokzoz.dluid.reducer.TestEnableReducer;
import org.kokzoz.dluid.reducer.TestResultClearReducer;
import org.kokzoz.dluid.reducer.TestSucceedReducer;
import org.kokzoz.dluid.reducer.TestTargetResultRefreshReducer;
import org.kokzoz.dluid.singleton.AppPropertiesSingleton;

@Data
public class TabModelTestController extends AbstractController {
    @FXML private VBox content;
    @FXML private VBox vBoxForTestData;
    @FXML private VBox vBoxForTestTask;
    @FXML private Label labelTestDataSetting;
    @FXML private Label labelTestTask;

    private ModelTestFeatureController modelTestFeatureController;
    private ModelTestTestingController modelTestTestingController;

    public Tab createView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/tab_model_test.fxml"));
        fxmlLoader.setController(this);
        AnchorPane content = fxmlLoader.load();

        Tab instanceTab = new Tab();
        instanceTab.setText(AppPropertiesSingleton.getInstance().get("frame.tabs.model.test"));
        instanceTab.setContent(content);
        return instanceTab;
    }

    @Override
    protected void initialize() throws Exception {
        modelTestFeatureController = new ModelTestFeatureController(this);
        modelTestTestingController = new ModelTestTestingController(this);
        vBoxForTestData.getChildren().add(modelTestFeatureController.createView());
        vBoxForTestTask.getChildren().add(modelTestTestingController.createView());
        labelTestDataSetting.setText(AppPropertiesSingleton.getInstance().get("frame.testTab.dataSetting.label"));
        labelTestTask.setText(AppPropertiesSingleton.getInstance().get("frame.testTab.testTask.label"));
        setSettingExpandAndDisable(true);
        setTestResultTableExpandAndDisable(true);
        AppFacade.addReducer(new TestSucceedReducer(this));
        AppFacade.addReducer(new TestResultClearReducer(this));
        AppFacade.addReducer(new TestEnableReducer(this));
        AppFacade.addReducer(new TestTargetResultRefreshReducer(this));
    }

    public void setSettingExpandAndDisable(boolean disable){
        modelTestFeatureController.getModelTestFeatureFileLoaderController().getTitledPane().setDisable(disable);
        modelTestFeatureController.getModelTestFeatureFileLoaderController().getTitledPane().setExpanded(!disable);
        modelTestFeatureController.getModelTestFeatureTableController().getTitledPane().setDisable(disable);
        modelTestFeatureController.getModelTestFeatureTableController().getTitledPane().setExpanded(!disable);
        modelTestTestingController.getModelTestTestingTaskController().getTitledPane().setDisable(disable);
        modelTestTestingController.getModelTestTestingTaskController().getTitledPane().setExpanded(!disable);
    }

    public void setTestResultTableExpandAndDisable(boolean disable){
        modelTestTestingController.getModelTestTestingResultTableController().getTitledPane().setDisable(disable);
        modelTestTestingController.getModelTestTestingResultTableController().getTitledPane().setExpanded(!disable);
    }
}
