package org.kok202.dluid;

import org.kok202.dluid.domain.action.Action;
import org.kok202.dluid.domain.action.ActionType;
import org.kok202.dluid.domain.action.Reducer;
import org.kok202.dluid.singleton.AppSingleton;

public class AppFacade {

    /*************************************************************************************************
     /* State machine
     *************************************************************************************************/
    public static void dispatchAction(ActionType actionType){
        AppSingleton.getInstance().getStateMachine().dispatchAction(actionType);
    }

    public static void dispatchAction(ActionType actionType, Object payload){
        AppSingleton.getInstance().getStateMachine().dispatchAction(actionType, payload);
    }

    public static void dispatchAction(Action action){
        AppSingleton.getInstance().getStateMachine().dispatchAction(action);
    }

    public static void addReducer(Reducer reducer){
        AppSingleton.getInstance().getStateMachine().addReducer(reducer);
    }

    /*************************************************************************************************
     /* Handling train tab
     *************************************************************************************************/
    public static void refreshModelInformation(){
        AppSingleton.getInstance()
                .getTabsController()
                .getTabModelTrainController()
                .getModelInformationController()
                .refreshModelInformation();
    }

    public static void refreshTrainingFileLoader(){
        AppSingleton.getInstance()
                .getTabsController()
                .getTabModelTrainController()
                .getModelTrainFileLoaderController()
                .refreshFileLoader();
    }

    public static void refreshTrainingLog(){
        AppSingleton.getInstance()
                .getTabsController()
                .getTabModelTrainController()
                .getModelTrainTaskController()
                .getLineChartAdapter()
                .clearChart();
        AppSingleton.getInstance()
                .getTabsController()
                .getTabModelTrainController()
                .getModelTrainTaskController()
                .getTextAreaTrainingLog()
                .clear();
    }

    public static void refreshTestInputLayerInformation(){
        AppSingleton.getInstance()
                .getTabsController()
                .getTabModelTestController()
                .getModelTestFeatureController()
                .getModelTestFeatureTableController()
                .refreshTestInputLayerInformation();
    }

    public static void refreshTestLog(){
        AppSingleton.getInstance()
                .getTabsController()
                .getTabModelTestController()
                .getModelTestTestingController()
                .getModelTestTestingTaskController()
                .getTextAreaTestLog()
                .clear();
    }

    public static void setTrainingAndTestSettingDisable(boolean disable){
        AppSingleton.getInstance()
                .getTabsController()
                .getTabModelTrainController()
                .setSettingExpandAndDisable(disable);
        AppSingleton.getInstance()
                .getTabsController()
                .getTabModelTestController()
                .setSettingExpandAndDisable(disable);
    }

    public static void setTrainingButtonDisable(boolean disable){
        AppSingleton.getInstance()
                .getTabsController()
                .getTabModelTrainController()
                .getModelTrainTaskController()
                .getButtonTrainingOneTime()
                .setDisable(disable);
        AppSingleton.getInstance()
                .getTabsController()
                .getTabModelTrainController()
                .getModelTrainTaskController()
                .getButtonTrainingNTime()
                .setDisable(disable);
        AppSingleton.getInstance()
                .getTabsController()
                .getTabModelTrainController()
                .getModelTrainTaskController()
                .getButtonTrainingStop()
                .setDisable(!disable);
        AppSingleton.getInstance()
                .getTabsController()
                .getTabModelTrainController()
                .getModelInformationController()
                .getButtonInitialize()
                .setDisable(disable);
    }

    /*************************************************************************************************
     /* Handling test tab
     *************************************************************************************************/
    public static String getTestInputLayerId(){
        return AppSingleton.getInstance()
                .getTabsController()
                .getTabModelTestController()
                .getModelTestFeatureController()
                .getModelTestFeatureTableController()
                .getMenuButtonTestTarget()
                .getText();
    }

    public static String getTestTargetResultLayerId(){
        return AppSingleton.getInstance()
                .getTabsController()
                .getTabModelTestController()
                .getModelTestTestingController()
                .getModelTestTestingTaskController()
                .getMenuButtonTestTargetResultLayer()
                .getText();
    }
}
