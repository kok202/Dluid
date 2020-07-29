package org.kok202.dluid.reducer;

import lombok.AllArgsConstructor;
import org.kok202.dluid.content.TabsController;
import org.kok202.dluid.domain.action.Action;
import org.kok202.dluid.domain.action.ActionType;
import org.kok202.dluid.domain.action.Reducer;

import java.util.Observable;

@AllArgsConstructor
public class RefreshTrainingFileLoaderReducer extends Reducer {
    TabsController tabsController;

    @Override
    public boolean support(Action action) {
        return action.getType() == ActionType.REFRESH_TRAINING_FILE_LOADER;
    }

    @Override
    public void update(Observable o, Action action) {
        tabsController.getTabModelTrainController().getModelTrainFileLoaderController().refreshFileLoader();
    }
}
