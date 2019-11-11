package org.kok202.deepblock.application.adapter.file;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.kok202.deepblock.ai.facade.AIFacade;
import org.kok202.deepblock.ai.singleton.structure.ManagedRecordSet;

public class TrainFeatureFileFinder extends ExtendedFileFinder {

    public TrainFeatureFileFinder(TextField textField, Button button) {
        super(textField, button);
    }

    @Override
    public ManagedRecordSet getManagedRecordSet() {
        return AIFacade.getTrainFeatureSet();
    }
}
