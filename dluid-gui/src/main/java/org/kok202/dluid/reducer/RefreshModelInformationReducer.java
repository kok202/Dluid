package org.kok202.dluid.reducer;

import lombok.AllArgsConstructor;
import org.kok202.dluid.content.train.ModelInformationController;
import org.kok202.dluid.domain.action.Action;
import org.kok202.dluid.domain.action.ActionType;
import org.kok202.dluid.domain.action.Reducer;

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
