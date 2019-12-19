package org.kok202.dluid;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Point2D;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.application.singleton.AppWidgetSingleton;

public class AppFacade {

    /*************************************************************************************************
     /* Canvas size
     *************************************************************************************************/
    public static double getCanvasWidgetHeight(){
        return AppWidgetSingleton.getInstance().getPrimaryStage().getHeight() -
                AppWidgetSingleton.getInstance().getMenuController().getMenuBar().getHeight() -
                AppWidgetSingleton.getInstance().getTabsController().getTabPane().getTabMaxHeight();
    }

    public static double getCanvasWidgetWidth(){
        double[] dividerPositions = AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelDesignController()
                .getMainSplitter()
                .getDividerPositions();
        return AppWidgetSingleton.getInstance().getBorderPane().getWidth() * (dividerPositions[1] - dividerPositions[0]);
    }

    public static void setResizingCanvasWidthListener(ChangeListener<? super Number> changeListener){
        AppWidgetSingleton.getInstance().getPrimaryStage().widthProperty().addListener(changeListener);
        AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelDesignController()
                .getMainSplitter()
                .getDividers().get(0)
                .positionProperty()
                .addListener(changeListener);
        AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelDesignController()
                .getMainSplitter()
                .getDividers().get(1)
                .positionProperty()
                .addListener(changeListener);
    }

    public static void setResizingCanvasHeightListener(ChangeListener<? super Number> changeListener){
        AppWidgetSingleton.getInstance().getPrimaryStage().heightProperty().addListener(changeListener);
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

    public static void clearTrainingLineChart(String text){
        AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelTrainController()
                .getModelTrainTaskController()
                .getLineChartTrainingChart();
        // TODO :
    }

    public static void drawTrainingLineChart(String text){
        AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelTrainController()
                .getModelTrainTaskController()
                .getLineChartTrainingChart();
        // TODO :
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
                .getButtonTrainingStop()
                .setDisable(disable);
        AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelTrainController()
                .getModelTrainTaskController()
                .getButtonTrainingStop()
                .setDisable(!disable);
    }

    public static void appendTextOnTrainingLog(String text){
        AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelTrainController()
                .getModelTrainTaskController()
                .getTextAreaTrainingLog()
                .appendText(text + "\n");
    }

    public static void clearTrainingLog(){
        AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelTrainController()
                .getModelTrainTaskController()
                .getTextAreaTrainingLog()
                .clear();
    }

    /*************************************************************************************************
     /* Handling test tab
     *************************************************************************************************/

    public static void appendTextOnTestingLog(String text){
        AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelTrainController()
                .getModelTrainTaskController()
                .getTextAreaTrainingLog()
                .appendText(text + "\n");
    }

    public static void clearTestingLog(){
        AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelTrainController()
                .getModelTrainTaskController()
                .getTextAreaTrainingLog()
                .clear();
    }

}
