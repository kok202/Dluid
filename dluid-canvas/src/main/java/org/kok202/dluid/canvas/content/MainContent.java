package org.kok202.dluid.canvas.content;

import javafx.scene.Group;
import lombok.Getter;

public class MainContent{
    private Group sceneRoot;

    @Getter
    private MaterialInsertionHandler materialInsertionHandler;

    public MainContent(Group sceneRoot) {
        this.sceneRoot = sceneRoot;
        this.sceneRoot.getChildren().add(new Coordinate());
        this.materialInsertionHandler = new MaterialInsertionHandler(sceneRoot);
    }
}