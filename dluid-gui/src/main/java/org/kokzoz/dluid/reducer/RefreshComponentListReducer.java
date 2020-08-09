package org.kokzoz.dluid.reducer;

import lombok.AllArgsConstructor;
import org.kokzoz.dluid.content.design.ComponentContainerController;
import org.kokzoz.dluid.domain.action.Action;
import org.kokzoz.dluid.domain.action.ActionType;
import org.kokzoz.dluid.domain.action.Reducer;
import org.kokzoz.dluid.domain.entity.Layer;

import java.util.Observable;

@AllArgsConstructor
public class RefreshComponentListReducer extends Reducer {
    ComponentContainerController componentContainerController;

    @Override
    public boolean support(Action action) {
        return action.getType() == ActionType.REFRESH_COMPONENT_LIST;
    }

    @Override
    public void update(Observable o, Action action) {
        Layer layer = (Layer) action.getPayload();
        componentContainerController.getComponentManager().clearComponentContainer();
        if(layer == null)
            return;
        componentContainerController.getComponentManager().attachComponentBoxOnList(layer);
        componentContainerController.getComponentManager().attachComponentBoxOnContainer();
    }
}
