package org.kok202.dluid;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.kok202.dluid.common.ExceptionHandler;
import org.kok202.dluid.content.TabsController;
import org.kok202.dluid.menu.MenuBarController;
import org.kok202.dluid.singleton.AppPropertiesSingleton;
import org.kok202.dluid.singleton.AppWidgetSingleton;

public class Main extends Application {
    private Stage primaryStage;
    private BorderPane borderPane;
    private MenuBarController menuBarController;
    private TabsController tabsController;

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler::catchException);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        setPrimaryStage(primaryStage);
        borderPane = new FXMLLoader(getClass().getResource("/frame/main.fxml")).load();
        borderPane.setTop(createTopFrame());
        borderPane.setCenter(createCenterFrame());
        setWidgetOnGlobalWidget();
        initWindowFrame(primaryStage, borderPane);
        showWindowFrame(primaryStage);
    }

    private void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
        primaryStage.getIcons().add(new Image("images/icon.png"));
    }

    private AnchorPane createTopFrame() throws Exception{
        menuBarController = new MenuBarController();
        return menuBarController.createView();
    }

    private AnchorPane createCenterFrame() throws Exception{
        tabsController = new TabsController();
        return tabsController.createView();
    }

    private void setWidgetOnGlobalWidget(){
        AppWidgetSingleton.getInstance().setPrimaryStage(primaryStage);
        AppWidgetSingleton.getInstance().setBorderPane(borderPane);
        AppWidgetSingleton.getInstance().setMenuBarController(menuBarController);
        AppWidgetSingleton.getInstance().setTabsController(tabsController);
        AppFacade.initialize();
    }

    private void initWindowFrame(Stage primaryStage, Parent fxmlRoot){
        Scene scene = new Scene(fxmlRoot,
                AppPropertiesSingleton.getInstance().getInt("frame.size.width"),
                AppPropertiesSingleton.getInstance().getInt("frame.size.height"));
        primaryStage.setTitle(AppPropertiesSingleton.getInstance().get("frame.title"));
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        applyTheme(fxmlRoot);
    }

    private void applyTheme(Parent root){
    }

    private void showWindowFrame(Stage primaryStage){
        primaryStage.show();
    }
}
