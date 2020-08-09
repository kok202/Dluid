package org.kokzoz.dluid.canvas.reducer;

import lombok.AllArgsConstructor;
import org.kokzoz.dluid.canvas.content.MaterialInsertionHandler;
import org.kokzoz.dluid.canvas.content.MaterialInsertionInfoHolder;
import org.kokzoz.dluid.domain.action.Action;
import org.kokzoz.dluid.domain.action.ActionType;
import org.kokzoz.dluid.domain.action.Reducer;

import java.util.Observable;

@AllArgsConstructor
public class MaterialInsertionHoveringReducer extends Reducer {
    MaterialInsertionHandler materialInsertionHandler;

    @Override
    public boolean support(Action action) {
        return action.getType() == ActionType.MATERIAL_INSERTION_HOVERING;
    }

    @Override
    public void update(Observable o, Action action) {
        MaterialInsertionInfoHolder materialInsertionInfoHolder = (MaterialInsertionInfoHolder) action.getPayload();
        materialInsertionHandler.hoveringListener(materialInsertionInfoHolder);
    }
}
