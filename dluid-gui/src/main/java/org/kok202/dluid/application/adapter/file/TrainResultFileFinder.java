package org.kok202.dluid.application.adapter.file;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.kok202.dluid.ai.AIFacade;
import org.kok202.dluid.ai.singleton.structure.ManagedRecordSet;

public class TrainResultFileFinder extends ExtendedFileFinder {

    public TrainResultFileFinder(TextField textField, Button button) {
        super(textField, button);
    }

    @Override
    public ManagedRecordSet getManagedRecordSet() {
        return AIFacade.getTrainResultSet();
    }
}
