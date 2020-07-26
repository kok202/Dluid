package org.kok202.dluid.canvas.scene;

import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;
import org.kok202.dluid.canvas.CanvasConstant;

@Getter
public class MainScene {
    @Setter
    private double sceneWidth;
    @Setter
    private double sceneHeight;
    private Camera camera;
    private Group sceneRoot;
    private SubScene sceneNode;

    public MainScene(double width, double height) {
        sceneWidth = width;
        sceneHeight = height;
        sceneRoot = new Group();
        sceneRoot.getChildren().add(new AmbientLight(Color.WHITE));
        sceneNode = new SubScene(sceneRoot, width, height, true, SceneAntialiasing.BALANCED);
        sceneNode.setCamera(camera = new Camera());
        sceneNode.setFill(CanvasConstant.COLOR_GRAY__);
        MouseEventListeners.setMouseEventListener(sceneNode, sceneRoot, camera);
    }

    public void refreshSceneSize(){
        sceneNode.setWidth(sceneWidth);
        sceneNode.setHeight(sceneHeight);
    }
}
