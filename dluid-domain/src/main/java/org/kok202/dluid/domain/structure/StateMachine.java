package org.kok202.dluid.domain.structure;

import org.kok202.dluid.domain.action.Action;
import org.kok202.dluid.domain.action.ActionType;

import java.util.Observable;

public class StateMachine {
    StateObservable observable;

    private static class StateObservable extends Observable{
        void dispatchAction(ActionType actionType){
            setChanged();
            notifyObservers(new Action(actionType));
        }
        void dispatchAction(ActionType actionType, Object payload){
            setChanged();
            notifyObservers(new Action(actionType, payload));
        }
        void dispatchAction(Action action){
            setChanged();
            notifyObservers(action);
        }
    }

    public StateMachine() {
        observable = new StateObservable();
    }

    public void dispatchAction(ActionType actionType){
        observable.dispatchAction(actionType);
    }

    public void dispatchAction(ActionType actionType, Object payload){
        observable.dispatchAction(actionType, payload);
    }

    public void dispatchAction(Action action){
        observable.dispatchAction(action);
    }
}
