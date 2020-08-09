package org.kokzoz.dluid.singleton;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.Data;
import org.kokzoz.dluid.content.TabsController;
import org.kokzoz.dluid.domain.structure.StateMachine;
import org.kokzoz.dluid.menu.MenuBarController;

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
