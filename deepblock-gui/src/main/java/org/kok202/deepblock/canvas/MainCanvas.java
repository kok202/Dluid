package org.kok202.deepblock.canvas;

import javafx.beans.value.ObservableValue;
import lombok.Getter;
import org.kok202.deepblock.application.global.AppWidgetSingleton;
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
        AppWidgetSingleton.getInstance().getPrimaryStage().widthProperty().addListener(this::resizingCanvasWidth);
        AppWidgetSingleton.getInstance().getPrimaryStage().heightProperty().addListener(this::resizingCanvasHeight);
        AppWidgetSingleton.getInstance().getMainSplitPane().getDividers().get(0).positionProperty().addListener(this::resizingCanvasWidth);
        AppWidgetSingleton.getInstance().getMainSplitPane().getDividers().get(1).positionProperty().addListener(this::resizingCanvasWidth);
    }

    private void resizingCanvasWidth(ObservableValue<? extends Number> observable, Number oldValue, Number newValue){
        double[] dividerPositons = AppWidgetSingleton.getInstance().getMainSplitPane().getDividerPositions();
        double canvasWidth = AppWidgetSingleton.getInstance().getPrimaryStage().getWidth() * (dividerPositons[1] - dividerPositons[0]);
        getMainScene().getSceneSize().setX(canvasWidth);
        getMainScene().refreshSceneSize();
    }

    private void resizingCanvasHeight(ObservableValue<? extends Number> observable, Number oldValue, Number newValue){
        double canvasHeight = AppWidgetSingleton.getInstance().getPrimaryStage().getHeight() - AppWidgetSingleton.getInstance().getMenuBar().getHeight();
        getMainScene().getSceneSize().setY(canvasHeight);
        getMainScene().refreshSceneSize();
    }
}
