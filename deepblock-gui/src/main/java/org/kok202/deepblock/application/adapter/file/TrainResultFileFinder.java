package org.kok202.deepblock.application.adapter.file;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.kok202.deepblock.ai.interfaces.AIInterface;
import org.kok202.deepblock.ai.singleton.structure.ManagedRecordSet;

public class TrainResultFileFinder extends ExtendedFileFinder {

    public TrainResultFileFinder(TextField textField, Button button) {
        super(textField, button);
    }

    @Override
    public ManagedRecordSet getManagedRecordSet() {
        return AIInterface.getTrainResultSet();
    }
}
