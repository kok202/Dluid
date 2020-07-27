package org.kok202.dluid.reducer;

import org.kok202.dluid.domain.action.Action;
import org.kok202.dluid.domain.action.ActionType;
import org.kok202.dluid.domain.action.Reducer;
import org.kok202.dluid.domain.entity.Layer;
import org.kok202.dluid.singleton.AppSingleton;

import java.util.Observable;

public class RefreshComponentListReducer extends Reducer {
    @Override
    public boolean support(Action action) {
        return action.getType() == ActionType.REFRESH_COMPONENT_LIST;
    }

    @Override
    public void update(Observable o, Action action) {
        Layer layer = (Layer) action.getPayload();
        AppSingleton.getInstance()
                .getTabsController()
                .getTabModelDesignController()
                .getComponentContainerController()
                .getComponentManager()
                .clearComponentContainer();

        if(layer == null)
            return;
        AppSingleton.getInstance()
                .getTabsController()
                .getTabModelDesignController()
                .getComponentContainerController()
                .getComponentManager()
                .attachComponentBoxOnList(layer);
        AppSingleton.getInstance()
                .getTabsController()
                .getTabModelDesignController()
                .getComponentContainerController()
                .getComponentManager()
                .attachComponentBoxOnContainer();
    }
}
