package org.kok202.dluid.canvas;

import lombok.Getter;
import org.kok202.dluid.canvas.content.MainContent;
import org.kok202.dluid.canvas.scene.MainScene;
import org.kok202.dluid.canvas.singleton.CanvasSingleton;

public class MainCanvas {
    @Getter
    private MainScene mainScene;

    @Getter
    private MainContent mainContent;

    public MainCanvas(double widthRatio, double heightRatio) {
        mainScene = new MainScene(widthRatio, heightRatio);
        mainContent = new MainContent(mainScene.getSceneRoot());
        CanvasSingleton.getInstance().setMainCanvas(this);
    }
}
