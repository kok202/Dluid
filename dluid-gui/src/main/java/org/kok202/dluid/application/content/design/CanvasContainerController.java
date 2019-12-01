package org.kok202.dluid.application.content.design;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import lombok.Getter;
import org.kok202.dluid.application.common.AbstractController;
import org.kok202.dluid.canvas.MainCanvas;

@Getter
public class CanvasContainerController extends AbstractController {

    private BlockConnectionManager blockConnectionManager;

    public Pane createView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/design/canvas_container.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        blockConnectionManager = new BlockConnectionManager();
        MainCanvas mainCanvas = new MainCanvas(600, 600);
        ((Pane)itself).getChildren().add(mainCanvas.getMainScene().getSceneNode());
        ((Pane)itself).getChildren().add(blockConnectionManager.getBlockConnectionFollower().createView());
        blockConnectionManager.setVisible(false);
    }
}
