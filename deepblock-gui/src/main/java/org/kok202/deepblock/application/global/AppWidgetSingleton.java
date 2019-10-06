package org.kok202.deepblock.application.global;

import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.NonNull;
import org.kok202.deepblock.application.content.ComponentContainerController;
import org.kok202.deepblock.application.content.ContentRootController;
import org.kok202.deepblock.application.content.MaterialContainerController;

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

    private AnchorPane contentRootPane;
    private SplitPane mainSplitPane;
    private MaterialContainerController materialContainerController;
    private ComponentContainerController componentContainerController;

    public void setMenuBar(@NonNull MenuBar menuBar) {
        this.menuBar = menuBar;
    }

    public void setPrimaryStage(@NonNull Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setContentRootController(@NonNull ContentRootController contentRootController) {
        this.contentRootController = contentRootController;
        this.contentRootPane = (AnchorPane) contentRootController.getItself();
        this.mainSplitPane = contentRootController.getMainSplitter();
        this.materialContainerController = contentRootController.getMaterialContainerController();
        this.componentContainerController = contentRootController.getComponentContainerController();
    }
}
