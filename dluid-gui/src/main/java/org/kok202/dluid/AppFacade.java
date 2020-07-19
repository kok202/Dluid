package org.kok202.dluid;

import javafx.geometry.Point2D;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.application.singleton.AppWidgetSingleton;
import org.kok202.dluid.canvas.CanvasFacade;

public class AppFacade {

    /*************************************************************************************************
     /* Canvas sizing
     *************************************************************************************************/
    public static void setCanvasResizeSubscriber(){
        AppWidgetSingleton.getInstance().getPrimaryStage().widthProperty().addListener(listener -> resizingCanvas());
        AppWidgetSingleton.getInstance().getPrimaryStage().heightProperty().addListener(listener -> resizingCanvas());
        AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelDesignController()
                .getMainSplitter()
                .getDividers().get(0)
                .positionProperty()
                .addListener(listener -> resizingCanvas());
        AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelDesignController()
                .getMainSplitter()
                .getDividers().get(1)
                .positionProperty()
                .addListener(listener -> resizingCanvas());
    }

    private static void resizingCanvas(){
        double canvasWidth = getCanvasWidgetWidth();
        double canvasHeight = getCanvasWidgetHeight();
        CanvasFacade.resizingCanvas(canvasWidth, canvasHeight);
    }

    private static double getCanvasWidgetHeight(){
        return AppWidgetSingleton.getInstance().getPrimaryStage().getHeight() -
                AppWidgetSingleton.getInstance().getMenuBarController().getMenuBar().getHeight() -
                AppWidgetSingleton.getInstance().getTabsController().getTabPane().getTabMaxHeight();
    }

    private static double getCanvasWidgetWidth(){
        double[] dividerPositions = AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelDesignController()
                .getMainSplitter()
                .getDividerPositions();
        return AppWidgetSingleton.getInstance().getBorderPane().getWidth() * (dividerPositions[1] - dividerPositions[0]);
    }

    /*************************************************************************************************
     /* Block connection manager
     *************************************************************************************************/
    public static void setConnectionArrowStart(Point2D point2D){
        AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelDesignController()
                .getCanvasContainerController()
                .getBlockConnectionManager()
                .setStart(point2D);
    }
    public static void setConnectionArrowEnd(Point2D point2D){
        AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelDesignController()
                .getCanvasContainerController()
                .getBlockConnectionManager()
                .setEnd(point2D);
    }

    public static void setConnectionArrowVisible(boolean visible){
        AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelDesignController()
                .getCanvasContainerController()
                .getBlockConnectionManager()
                .setVisible(visible);
    }

    public static boolean isConnectionArrowUpward(){
        return AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelDesignController()
                .getCanvasContainerController()
                .getBlockConnectionManager()
                .isUpward();
    }

    /*************************************************************************************************
     /* Component container (split right container)
     *************************************************************************************************/
    public static void clearComponentContainer(){
        AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelDesignController()
                .getComponentContainerController()
                .getComponentManager()
                .clearComponentContainer();
    }

    public static void refreshComponentContainer(Layer layer){
        AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelDesignController()
                .getComponentContainerController()
                .getComponentManager()
                .refreshContainerByLayer(layer);
    }

    /*************************************************************************************************
     /* Handling train tab
     *************************************************************************************************/

    public static void refreshModelInformation(){
        AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelTrainController()
                .getModelInformationController()
                .refreshModelInformation();
    }

    public static void refreshTrainingFileLoader(){
        AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelTrainController()
                .getModelTrainFileLoaderController()
                .refreshFileLoader();
    }

    public static void refreshTrainingLog(){
        AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelTrainController()
                .getModelTrainTaskController()
                .getLineChartAdapter()
                .clearChart();
        AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelTrainController()
                .getModelTrainTaskController()
                .getTextAreaTrainingLog()
                .clear();
    }

    public static void refreshTestInputLayerInformation(){
        AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelTestController()
                .getModelTestFeatureController()
                .getModelTestFeatureTableController()
                .refreshTestInputLayerInformation();
    }

    public static void refreshTestLog(){
        AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelTestController()
                .getModelTestTestingController()
                .getModelTestTestingTaskController()
                .getTextAreaTestLog()
                .clear();
    }

    public static void setTrainingAndTestSettingDisable(boolean disable){
        AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelTrainController()
                .setSettingExpandAndDisable(disable);
        AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelTestController()
                .setSettingExpandAndDisable(disable);
    }

    public static void setTrainingButtonDisable(boolean disable){
        AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelTrainController()
                .getModelTrainTaskController()
                .getButtonTrainingOneTime()
                .setDisable(disable);
        AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelTrainController()
                .getModelTrainTaskController()
                .getButtonTrainingNTime()
                .setDisable(disable);
        AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelTrainController()
                .getModelTrainTaskController()
                .getButtonTrainingStop()
                .setDisable(!disable);
        AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelTrainController()
                .getModelInformationController()
                .getButtonInitialize()
                .setDisable(disable);
    }

    /*************************************************************************************************
     /* Handling test tab
     *************************************************************************************************/
    public static void setTestButtonDisable(boolean disable){
        AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelTestController()
                .getModelTestTestingController()
                .getModelTestTestingTaskController()
                .getButtonTest()
                .setDisable(disable);
    }

    public static String getTestInputLayerId(){
        return AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelTestController()
                .getModelTestFeatureController()
                .getModelTestFeatureTableController()
                .getMenuButtonTestTarget()
                .getText();
    }

    public static String getTestTargetResultLayerId(){
        return AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelTestController()
                .getModelTestTestingController()
                .getModelTestTestingTaskController()
                .getMenuButtonTestTargetResultLayer()
                .getText();
    }

    public static void notifyTestDone() {
        AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelTestController()
                .setTestResultTableExpandAndDisable(false);
    }

    public static void clearTestResultTableView() {
        AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelTestController()
                .getModelTestTestingController()
                .getModelTestTestingResultTableController()
                .clearTestResultTableView();
    }
}
