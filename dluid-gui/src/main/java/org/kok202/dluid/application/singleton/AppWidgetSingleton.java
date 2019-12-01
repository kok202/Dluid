package org.kok202.dluid.application.singleton;

import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.Data;
import org.kok202.dluid.application.content.TabsController;

@Data
public class AppWidgetSingleton {
    private static class FrameControllerHolder{
        private static final AppWidgetSingleton instance = new AppWidgetSingleton();
    }

    public static AppWidgetSingleton getInstance(){
        return AppWidgetSingleton.FrameControllerHolder.instance;
    }

    private AppWidgetSingleton(){}

    private Stage primaryStage;
    private BorderPane borderPane;
    private MenuBar menuBar;
    private TabsController tabsController;
}
