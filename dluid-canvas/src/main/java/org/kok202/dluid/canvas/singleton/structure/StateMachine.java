package org.kok202.dluid.canvas.singleton.structure;

import org.kok202.dluid.domain.action.Action;
import org.kok202.dluid.domain.action.ActionType;

import java.util.Observable;

public class StateMachine extends Observable {
    public void dispatchAction(ActionType actionType){
        notifyObservers(new Action(actionType));
    }
    public void dispatchAction(Action action){
        notifyObservers(action);
    }
}
