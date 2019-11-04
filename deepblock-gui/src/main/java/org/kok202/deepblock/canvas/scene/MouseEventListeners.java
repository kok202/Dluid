package org.kok202.deepblock.canvas.scene;

import javafx.scene.Group;
import javafx.scene.SubScene;
import org.kok202.deepblock.canvas.content.BlockConnectionHandler;
import org.kok202.deepblock.canvas.content.BlockMovingHandler;
import org.kok202.deepblock.canvas.content.BlockPicker;

public class MouseEventListeners {

    public static void setMouseEventListener(SubScene sceneNode, Group sceneRoot, Camera camera){
        sceneNode.setOnScroll(scrollEvent -> {
            camera.setOnScroll(scrollEvent);
        });
        sceneNode.setOnMousePressed(mouseEvent -> {
            camera.setOnMousePressed(mouseEvent);
            BlockMovingHandler.setOnMousePressed(mouseEvent);
            BlockConnectionHandler.setOnMousePressed(sceneRoot, mouseEvent);
        });
        sceneNode.setOnMouseDragged(mouseEvent -> {
            camera.setOnMouseDragged(mouseEvent);
            BlockMovingHandler.setOnMouseDragged(mouseEvent);
        });
        sceneNode.setOnMouseReleased(mouseEvent -> {
            BlockMovingHandler.setOnMouseReleased(mouseEvent);
        });
        sceneNode.setOnMouseClicked(mouseEvent -> {
            BlockPicker.setOnMouseClicked(mouseEvent);
            BlockConnectionHandler.setOnMouseClicked(sceneRoot, mouseEvent);
        });
        sceneNode.setOnMouseMoved(mouseEvent -> {
            BlockConnectionHandler.setOnMouseMoved(sceneRoot, mouseEvent);
        });
    }
}
