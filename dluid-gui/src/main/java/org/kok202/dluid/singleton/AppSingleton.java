package org.kok202.dluid.singleton;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.Data;
import org.kok202.dluid.content.TabsController;
import org.kok202.dluid.domain.structure.StateMachine;
import org.kok202.dluid.menu.MenuBarController;

@Data
public class AppSingleton {
    private static class FrameControllerHolder{
        private static final AppSingleton instance = new AppSingleton();
    }

    public static AppSingleton getInstance(){
        return AppSingleton.FrameControllerHolder.instance;
    }

    private AppSingleton(){
        stateMachine = new StateMachine();
    }

    private Stage primaryStage;
    private BorderPane borderPane;
    private MenuBarController menuBarController;
    private TabsController tabsController;
    private StateMachine stateMachine;
}
