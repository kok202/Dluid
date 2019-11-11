package org.kok202.deepblock.canvas;

import javafx.beans.value.ObservableValue;
import lombok.Getter;
import org.kok202.deepblock.application.interfaces.AppInterface;
import org.kok202.deepblock.canvas.content.MainContent;
import org.kok202.deepblock.canvas.scene.MainScene;
import org.kok202.deepblock.canvas.singleton.CanvasSingleton;

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

    public void setResizingListener(){
        AppInterface.setResizingCanvasWidthListener(this::resizingCanvasWidth);
        AppInterface.setResizingCanvasHeightListener(this::resizingCanvasHeight);
    }

    private void resizingCanvasWidth(ObservableValue<? extends Number> observable, Number oldValue, Number newValue){
        double canvasWidth = AppInterface.getCanvasWidgetWidth();
        getMainScene().getSceneSize().setX(canvasWidth);
        getMainScene().refreshSceneSize();
    }

    private void resizingCanvasHeight(ObservableValue<? extends Number> observable, Number oldValue, Number newValue){
        double canvasHeight = AppInterface.getCanvasWidgetHeight();
        getMainScene().getSceneSize().setY(canvasHeight);
        getMainScene().refreshSceneSize();
    }
}
