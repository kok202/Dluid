package org.kok202.dluid.application.adapter.file;

import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import org.kok202.dluid.ai.AIFacade;
import org.kok202.dluid.ai.singleton.structure.ManagedRecordSet;

public class TrainResultFileFinder extends TrainDataFileFinder {

    public TrainResultFileFinder(TextField textField, Button button, MenuButton menuButtonTrainingTarget) {
        super(textField, button, menuButtonTrainingTarget);
    }

    @Override
    public ManagedRecordSet getManagedRecordSet() {
        return AIFacade.getTrainResultSet(getTargetLayerId());
    }
}
