package org.kok202.deepblock.application.content.model;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Data;
import org.kok202.deepblock.application.common.AbstractController;
import org.kok202.deepblock.application.content.model.test.ModelFeatureSetController;
import org.kok202.deepblock.application.content.model.test.ModelFeatureSetLoaderController;
import org.kok202.deepblock.application.content.model.test.ModelResultSetController;
import org.kok202.deepblock.application.content.model.test.ModelTestController;
import org.kok202.deepblock.application.global.AppPropertiesSingleton;

@Data
public class TabModelTestController extends AbstractController {
    @FXML
    private VBox content;

    private ModelFeatureSetLoaderController modelFeatureSetLoaderController;
    private ModelFeatureSetController modelFeatureSetController;
    private ModelTestController modelTestController;
    private ModelResultSetController modelResultSetController;

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
        modelFeatureSetLoaderController = new ModelFeatureSetLoaderController(this);
        modelFeatureSetController = new ModelFeatureSetController(this);
        modelTestController = new ModelTestController(this);
        modelResultSetController = new ModelResultSetController(this);
        content.getChildren().add(modelFeatureSetLoaderController.createView());
        content.getChildren().add(modelFeatureSetController.createView());
        content.getChildren().add(modelTestController.createView());
        content.getChildren().add(modelResultSetController.createView());
    }
}
