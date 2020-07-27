package org.kok202.dluid;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.kok202.dluid.canvas.CanvasFacade;
import org.kok202.dluid.common.ExceptionHandler;
import org.kok202.dluid.content.TabsController;
import org.kok202.dluid.menu.MenuBarController;
import org.kok202.dluid.singleton.AppPropertiesSingleton;
import org.kok202.dluid.singleton.AppSingleton;

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
        initializeCanvas();
        initializeWindowFrame(primaryStage, borderPane);
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
        AppSingleton.getInstance().setPrimaryStage(primaryStage);
        AppSingleton.getInstance().setBorderPane(borderPane);
        AppSingleton.getInstance().setMenuBarController(menuBarController);
        AppSingleton.getInstance().setTabsController(tabsController);
    }

    private void initializeCanvas(){
        setCanvasResizeSubscriber();
    }

    private void initializeWindowFrame(Stage primaryStage, Parent fxmlRoot){
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

    private void setCanvasResizeSubscriber(){
        AppSingleton.getInstance().getPrimaryStage().widthProperty().addListener(listener -> resizingCanvas());
        AppSingleton.getInstance().getPrimaryStage().heightProperty().addListener(listener -> resizingCanvas());
        AppSingleton.getInstance()
                .getTabsController()
                .getTabModelDesignController()
                .getMainSplitter()
                .getDividers().get(0)
                .positionProperty()
                .addListener(listener -> resizingCanvas());
        AppSingleton.getInstance()
                .getTabsController()
                .getTabModelDesignController()
                .getMainSplitter()
                .getDividers().get(1)
                .positionProperty()
                .addListener(listener -> resizingCanvas());
    }

    private void resizingCanvas(){
        double canvasWidth = getCanvasWidgetWidth();
        double canvasHeight = getCanvasWidgetHeight();
        CanvasFacade.resizingCanvas(canvasWidth, canvasHeight);
    }

    private double getCanvasWidgetHeight(){
        return AppSingleton.getInstance().getPrimaryStage().getHeight() -
                AppSingleton.getInstance().getMenuBarController().getMenuBar().getHeight() -
                AppSingleton.getInstance().getTabsController().getTabPane().getTabMaxHeight();
    }

    private static double getCanvasWidgetWidth(){
        double[] dividerPositions = AppSingleton.getInstance()
                .getTabsController()
                .getTabModelDesignController()
                .getMainSplitter()
                .getDividerPositions();
        return AppSingleton.getInstance().getBorderPane().getWidth() * (dividerPositions[1] - dividerPositions[0]);
    }

}
