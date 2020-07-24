package org.kok202.dluid;

import org.kok202.dluid.application.reducer.ConnectionMoveReducer;
import org.kok202.dluid.application.reducer.ConnectionReleaseReducer;
import org.kok202.dluid.application.reducer.ConnectionStartReducer;
import org.kok202.dluid.application.singleton.AppWidgetSingleton;
import org.kok202.dluid.canvas.CanvasFacade;

public class AppFacade {

    /*************************************************************************************************
     /* Canvas sizing
     *************************************************************************************************/
    public static void initialize(){
        setCanvasResizeSubscriber();
        AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelDesignController()
                .getComponentContainerController()
                .getComponentManager()
                .addCanvasObserver();
        CanvasFacade.addReducer(new ConnectionStartReducer());
        CanvasFacade.addReducer(new ConnectionMoveReducer());
        CanvasFacade.addReducer(new ConnectionReleaseReducer());
    }

    private static void setCanvasResizeSubscriber(){
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
