package org.kok202.dluid.application.adapter;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PopUpExtension {
    private Stage popUpStage;
    private String title;
    private int width;
    private int height;
    private Parent popUpSceneRoot;
    private Runnable callbackAfterLoad;

    public PopUpExtension setTitle(String title) {
        this.title = title;
        return this;
    }

    public PopUpExtension setWidth(int width) {
        this.width = width;
        return this;
    }

    public PopUpExtension setHeight(int height) {
        this.height = height;
        return this;
    }

    public PopUpExtension setPopUpSceneRoot(Parent popUpSceneRoot) {
        this.popUpSceneRoot = popUpSceneRoot;
        return this;
    }

    public PopUpExtension setCallbackAfterLoad(Runnable callbackAfterLoad) {
        this.callbackAfterLoad = callbackAfterLoad;
        return this;
    }

    public void show(){
        popUpStage = new Stage();
        popUpStage.setTitle(title);
        popUpStage.setScene(new Scene(popUpSceneRoot, width, height));
        popUpStage.show();
    }

    public void load(){
        popUpStage.close();
        if(callbackAfterLoad != null)
            callbackAfterLoad.run();
    }

    public void cancel(){
        popUpStage.close();
    }
}
