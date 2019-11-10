package org.kok202.deepblock.canvas.scene;

import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.paint.Color;
import lombok.Getter;
import org.kok202.deepblock.canvas.interfaces.CanvasConstant;
import org.kok202.deepblock.domain.structure.Vector2D;

@Getter
public class MainScene {
    private Camera camera;
    private Vector2D sceneSize;
    private Group sceneRoot;
    private SubScene sceneNode;

    public MainScene(double width, double height) {
        sceneSize = new Vector2D(width, height);
        sceneRoot = new Group();
        sceneRoot.getChildren().add(new AmbientLight(Color.WHITE));
        sceneNode = new SubScene(sceneRoot, width, height, true, SceneAntialiasing.BALANCED);
        sceneNode.setCamera(camera = new Camera());
        sceneNode.setFill(CanvasConstant.COLOR_DARK_GRAY);
        MouseEventListeners.setMouseEventListener(sceneNode, sceneRoot, camera);
    }

    public void refreshSceneSize(){
        sceneNode.setWidth(sceneSize.getX());
        sceneNode.setHeight(sceneSize.getY());
    }
}
