package org.kok202.dluid.canvas.singleton.structure;

import org.kok202.dluid.domain.action.Action;
import org.kok202.dluid.domain.action.ActionType;

import java.util.Observable;

public class StateMachine extends Observable {
    public void dispatchAction(ActionType actionType){
        setChanged();
        notifyObservers(new Action(actionType));
    }
    public void dispatchAction(ActionType actionType, Object payload){
        setChanged();
        notifyObservers(new Action(actionType, payload));
    }
    public void dispatchAction(Action action){
        setChanged();
        notifyObservers(action);
    }
}
