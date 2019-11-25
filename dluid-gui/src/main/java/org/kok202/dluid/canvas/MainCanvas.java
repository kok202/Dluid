package org.kok202.dluid.canvas;

import javafx.beans.value.ObservableValue;
import lombok.Getter;
import org.kok202.dluid.AppFacade;
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

    public void setResizingListener(){
        AppFacade.setResizingCanvasWidthListener(this::resizingCanvasWidth);
        AppFacade.setResizingCanvasHeightListener(this::resizingCanvasHeight);
    }

    private void resizingCanvasWidth(ObservableValue<? extends Number> observable, Number oldValue, Number newValue){
        double canvasWidth = AppFacade.getCanvasWidgetWidth();
        getMainScene().getSceneSize().setX(canvasWidth);
        getMainScene().refreshSceneSize();
    }

    private void resizingCanvasHeight(ObservableValue<? extends Number> observable, Number oldValue, Number newValue){
        double canvasHeight = AppFacade.getCanvasWidgetHeight();
        getMainScene().getSceneSize().setY(canvasHeight);
        getMainScene().refreshSceneSize();
    }
}
