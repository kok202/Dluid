package org.kok202.deepblock.application.facade;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Point2D;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.application.singleton.AppWidgetSingleton;

public class AppFacade {

    /*************************************************************************************************
     /* Canvas size
     *************************************************************************************************/
    public static double getCanvasWidgetHeight(){
        return AppWidgetSingleton.getInstance().getPrimaryStage().getHeight() - AppWidgetSingleton.getInstance().getMenuBar().getHeight();
    }

    public static double getCanvasWidgetWidth(){
        double[] dividerPositions = AppWidgetSingleton.getInstance().getMainSplitPane().getDividerPositions();
        return AppWidgetSingleton.getInstance().getPrimaryStage().getWidth() * (dividerPositions[1] - dividerPositions[0]);
    }

    public static void setResizingCanvasWidthListener(ChangeListener<? super Number> changeListener){
        AppWidgetSingleton.getInstance().getPrimaryStage().widthProperty().addListener(changeListener);
        AppWidgetSingleton.getInstance().getMainSplitPane().getDividers().get(0).positionProperty().addListener(changeListener);
        AppWidgetSingleton.getInstance().getMainSplitPane().getDividers().get(1).positionProperty().addListener(changeListener);
    }

    public static void setResizingCanvasHeightListener(ChangeListener<? super Number> changeListener){
        AppWidgetSingleton.getInstance().getPrimaryStage().heightProperty().addListener(changeListener);
    }

    /*************************************************************************************************
     /* Block connection manager
     *************************************************************************************************/
    public static void setConnectionArrowStart(Point2D point2D){
        AppWidgetSingleton.getInstance()
                .getContentRootController()
                .getTabsController()
                .getTabModelDesignController()
                .getBlockConnectionManager()
                .setStart(point2D);
    }
    public static void setConnectionArrowEnd(Point2D point2D){
        AppWidgetSingleton.getInstance()
                .getContentRootController()
                .getTabsController()
                .getTabModelDesignController()
                .getBlockConnectionManager()
                .setEnd(point2D);
    }

    public static void setConnectionArrowVisible(boolean visible){
        AppWidgetSingleton.getInstance()
                .getContentRootController()
                .getTabsController()
                .getTabModelDesignController()
                .getBlockConnectionManager()
                .setVisible(visible);
    }

    public static boolean isConnectionArrowUpward(){
        return AppWidgetSingleton.getInstance()
                .getContentRootController()
                .getTabsController()
                .getTabModelDesignController()
                .getBlockConnectionManager()
                .isUpward();
    }

    /*************************************************************************************************
     /* Component container (split right container)
     *************************************************************************************************/
    public static void refreshComponentContainer(Layer layer){
        AppWidgetSingleton.getInstance()
                .getComponentContainerController()
                .getComponentManager()
                .refreshContainerByLayer(layer);
    }


}
