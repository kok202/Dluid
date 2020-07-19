package org.kok202.dluid.domain.action;

import java.util.Observable;
import java.util.Observer;

public abstract class Reducer implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        Action action = (Action) arg;
        if(support(action)){
            update(o, action);
        }
    }

    public abstract boolean support(Action action);

    public abstract void update(Observable o, Action action);
}
