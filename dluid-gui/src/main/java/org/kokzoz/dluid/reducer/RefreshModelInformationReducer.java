package org.kokzoz.dluid.reducer;

import lombok.AllArgsConstructor;
import org.kokzoz.dluid.content.train.ModelInformationController;
import org.kokzoz.dluid.domain.action.Action;
import org.kokzoz.dluid.domain.action.ActionType;
import org.kokzoz.dluid.domain.action.Reducer;

import java.util.Observable;

@AllArgsConstructor
public class RefreshModelInformationReducer extends Reducer {
    ModelInformationController modelInformationController;

    @Override
    public boolean support(Action action) {
        return action.getType() == ActionType.REFRESH_MODEL_INFORMATION;
    }

    @Override
    public void update(Observable o, Action action) {
        modelInformationController.refreshModelInformation();
    }
}
