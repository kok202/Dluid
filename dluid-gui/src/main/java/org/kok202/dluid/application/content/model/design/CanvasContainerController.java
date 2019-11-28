package org.kok202.dluid.application.content.model.design;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import org.kok202.dluid.application.common.AbstractController;
import org.kok202.dluid.canvas.MainCanvas;

public class CanvasContainerController extends AbstractController {
    private MainCanvas mainCanvas;

    @Getter
    private BlockConnectionManager blockConnectionManager;

    public AnchorPane createView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/design/canvas_container.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
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
