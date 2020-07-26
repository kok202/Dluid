package org.kok202.dluid.singleton;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.Data;
import org.kok202.dluid.content.TabsController;
import org.kok202.dluid.menu.MenuBarController;

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
    private MenuBarController menuBarController;
    private TabsController tabsController;
}
