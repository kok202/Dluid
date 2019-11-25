package org.kok202.dluid.canvas.content;

import javafx.scene.Group;
import lombok.Getter;

public class MainContent{
    private Group sceneRoot;

    @Getter
    private BlockInsertionHandler blockInsertionHandler;

    public MainContent(Group sceneRoot) {
        this.sceneRoot = sceneRoot;
        this.sceneRoot.getChildren().add(new Coordinate());
        this.blockInsertionHandler = new BlockInsertionHandler(sceneRoot);
    }
}