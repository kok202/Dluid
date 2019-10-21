package org.kok202.deepblock.canvas.scene;

import javafx.scene.Group;
import javafx.scene.SubScene;
import org.kok202.deepblock.canvas.content.BlockConnectionHandler;
import org.kok202.deepblock.canvas.content.BlockDraggingHandler;
import org.kok202.deepblock.canvas.content.BlockPicker;

public class MouseEventListeners {

    public static void setMouseEventListener(SubScene sceneNode, Group sceneRoot, Camera camera){
        sceneNode.setOnScroll(scrollEvent -> {
            camera.setOnScroll(scrollEvent);
        });
        sceneNode.setOnMousePressed(mouseEvent -> {
            camera.setOnMousePressed(mouseEvent);
            BlockDraggingHandler.setOnMousePressed(mouseEvent);
        });
        sceneNode.setOnMouseDragged(mouseEvent -> {
            camera.setOnMouseDragged(mouseEvent);
            BlockDraggingHandler.setOnMouseDragged(mouseEvent);
        });
        sceneNode.setOnMouseReleased(mouseEvent -> {
            BlockDraggingHandler.setOnMouseReleased(mouseEvent);
        });
        sceneNode.setOnMouseClicked(mouseEvent -> {
            BlockPicker.setOnMouseClicked(mouseEvent);
            BlockConnectionHandler.setOnMouseClicked(mouseEvent, sceneRoot);
        });
        sceneNode.setOnKeyTyped(keyEvent -> {
            BlockConnectionHandler.setOnKeyTyped(keyEvent);
        });
    }
}
