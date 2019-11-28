package org.kok202.dluid.application.singleton;

import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.NonNull;
import org.kok202.dluid.application.content.TabsController;

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
    private TabsController tabsController;

    public void setMenuBar(@NonNull MenuBar menuBar) {
        this.menuBar = menuBar;
    }

    public void setPrimaryStage(@NonNull Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setTabsController(@NonNull TabsController tabsController) {
        this.tabsController = tabsController;
    }
}
