package org.kok202.deepblock.application.content.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import org.kok202.deepblock.application.common.AbstractController;
import org.kok202.deepblock.application.global.AppPropertiesSingleton;
import org.kok202.deepblock.canvas.MainCanvas;

public class TabModelDesignController extends AbstractController {
    private MainCanvas mainCanvas;

    public Tab createView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/model/tab_model_design.fxml"));
        fxmlLoader.setController(this);
        AnchorPane content = fxmlLoader.load();

        Tab instanceTab = new Tab();
        instanceTab.setText(AppPropertiesSingleton.getInstance().get("frame.tabs.model.design"));
        instanceTab.setContent(content);
        return instanceTab;
    }

    @Override
    protected void initialize() throws Exception {
        mainCanvas = new MainCanvas(1200, 600);
        ((AnchorPane)itself).getChildren().add(mainCanvas.getMainScene().getSceneNode());
    }
}
