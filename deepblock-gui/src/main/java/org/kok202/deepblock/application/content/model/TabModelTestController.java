package org.kok202.deepblock.application.content.model;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Data;
import org.kok202.deepblock.application.common.AbstractController;
import org.kok202.deepblock.application.content.model.test.ModelTestController;
import org.kok202.deepblock.application.content.model.test.ModelTestFeatureSetController;
import org.kok202.deepblock.application.content.model.test.ModelTestFeatureSetLoaderController;
import org.kok202.deepblock.application.content.model.test.ModelTestResultSetController;
import org.kok202.deepblock.application.singleton.AppPropertiesSingleton;

@Data
public class TabModelTestController extends AbstractController {
    @FXML
    private VBox content;

    private ModelTestFeatureSetLoaderController modelTestFeatureSetLoaderController;
    private ModelTestFeatureSetController modelTestFeatureSetController;
    private ModelTestController modelTestController;
    private ModelTestResultSetController modelTestResultSetController;

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
        modelTestFeatureSetLoaderController = new ModelTestFeatureSetLoaderController(this);
        modelTestFeatureSetController = new ModelTestFeatureSetController(this);
        modelTestController = new ModelTestController(this);
        modelTestResultSetController = new ModelTestResultSetController(this);
        content.getChildren().add(modelTestFeatureSetLoaderController.createView());
        content.getChildren().add(modelTestFeatureSetController.createView());
        content.getChildren().add(modelTestController.createView());
        content.getChildren().add(modelTestResultSetController.createView());
    }
}
