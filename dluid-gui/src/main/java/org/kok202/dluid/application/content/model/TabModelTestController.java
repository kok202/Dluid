package org.kok202.dluid.application.content.model;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Data;
import org.kok202.dluid.application.common.AbstractController;
import org.kok202.dluid.application.content.model.test.ModelTestFeatureController;
import org.kok202.dluid.application.content.model.test.ModelTestTestingController;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;

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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/model/tab_model_test.fxml"));
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
    }
}
