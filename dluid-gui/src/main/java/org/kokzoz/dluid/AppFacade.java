package org.kokzoz.dluid;

import org.kokzoz.dluid.domain.action.Action;
import org.kokzoz.dluid.domain.action.ActionType;
import org.kokzoz.dluid.domain.action.Reducer;
import org.kokzoz.dluid.singleton.AppSingleton;

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
