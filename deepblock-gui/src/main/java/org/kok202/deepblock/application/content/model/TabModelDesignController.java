package org.kok202.deepblock.application.content.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import org.kok202.deepblock.application.common.AbstractController;
import org.kok202.deepblock.application.content.model.design.BlockConnectionManager;
import org.kok202.deepblock.application.singleton.AppPropertiesSingleton;
import org.kok202.deepblock.canvas.MainCanvas;

public class TabModelDesignController extends AbstractController {
    private MainCanvas mainCanvas;

    @Getter
    private BlockConnectionManager blockConnectionManager;

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
        blockConnectionManager = new BlockConnectionManager();

        AnchorPane modelDesignTab = (AnchorPane)itself;
        modelDesignTab.getChildren().add(mainCanvas.getMainScene().getSceneNode());
        modelDesignTab.getChildren().add(blockConnectionManager.getBlockConnectionFollower().createView());
        blockConnectionManager.setVisible(false);
    }
}
