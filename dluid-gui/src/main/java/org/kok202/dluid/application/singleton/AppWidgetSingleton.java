package org.kok202.dluid.application.singleton;

import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.NonNull;
import org.kok202.dluid.application.content.ContentRootController;

@Getter
public class AppWidgetSingleton {
    private static class FrameControllerHolder{
        private static final AppWidgetSingleton instance = new AppWidgetSingleton();
    }

    public static AppWidgetSingleton getInstance(){
        return AppWidgetSingleton.FrameControllerHolder.instance;
    }

    private AppWidgetSingleton(){}

    private Stage primaryStage;
    private MenuBar menuBar;
    private ContentRootController contentRootController;

    public void setMenuBar(@NonNull MenuBar menuBar) {
        this.menuBar = menuBar;
    }

    public void setPrimaryStage(@NonNull Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setContentRootController(@NonNull ContentRootController contentRootController) {
        this.contentRootController = contentRootController;
    }
}
