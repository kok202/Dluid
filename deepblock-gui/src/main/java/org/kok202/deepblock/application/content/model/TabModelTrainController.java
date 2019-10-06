package org.kok202.deepblock.application.content.model;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.kok202.deepblock.application.common.AbstractController;
import org.kok202.deepblock.application.content.model.train.ModelInfoController;
import org.kok202.deepblock.application.content.model.train.ModelTrainController;
import org.kok202.deepblock.application.content.model.train.ModelTrainParamController;
import org.kok202.deepblock.application.global.AppPropertiesSingleton;


public class TabModelTrainController extends AbstractController {
    @FXML
    private VBox content;

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
        content.getChildren().add(new ModelInfoController().createView());
        content.getChildren().add(new ModelTrainParamController().createView());
        content.getChildren().add(new ModelTrainController().createView());
    }
}
