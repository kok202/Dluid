package org.kok202.dluid.canvas.reducer;

import lombok.AllArgsConstructor;
import org.kok202.dluid.canvas.content.MaterialInsertionHandler;
import org.kok202.dluid.canvas.content.MaterialInsertionInfoHolder;
import org.kok202.dluid.domain.action.Action;
import org.kok202.dluid.domain.action.ActionType;
import org.kok202.dluid.domain.action.Reducer;

import java.util.Observable;

@AllArgsConstructor
public class MaterialInsertionDoneReducer extends Reducer {
    MaterialInsertionHandler materialInsertionHandler;

    @Override
    public boolean support(Action action) {
        return action.getType() == ActionType.MATERIAL_INSERTION_DONE;
    }

    @Override
    public void update(Observable o, Action action) {
        MaterialInsertionInfoHolder materialInsertionInfoHolder = (MaterialInsertionInfoHolder) action.getPayload();
        materialInsertionHandler.doneListener(materialInsertionInfoHolder);
    }
}
