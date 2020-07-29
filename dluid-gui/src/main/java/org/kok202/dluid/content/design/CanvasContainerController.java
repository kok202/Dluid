package org.kok202.dluid.content.design;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import lombok.Getter;
import org.kok202.dluid.canvas.CanvasFacade;
import org.kok202.dluid.canvas.MainCanvas;
import org.kok202.dluid.common.AbstractController;
import org.kok202.dluid.reducer.ConnectionMoveReducer;
import org.kok202.dluid.reducer.ConnectionReleaseReducer;
import org.kok202.dluid.reducer.ConnectionStartReducer;

@Getter
public class CanvasContainerController extends AbstractController {

    private BlockConnectionFollower blockConnectionFollower;

    public Pane createView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/design/canvas_container.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        blockConnectionFollower = new BlockConnectionFollower();
        MainCanvas mainCanvas = new MainCanvas(600, 600);
        ((Pane)itself).getChildren().add(mainCanvas.getMainScene().getSceneNode());
        ((Pane)itself).getChildren().add(blockConnectionFollower.createView());
        blockConnectionFollower.setVisible(false);
        CanvasFacade.addReducer(new ConnectionStartReducer(this));
        CanvasFacade.addReducer(new ConnectionMoveReducer(this));
        CanvasFacade.addReducer(new ConnectionReleaseReducer(this));
    }
}
