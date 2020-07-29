package org.kok202.dluid.adapter.file;

import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;

public abstract class TrainDataFileFinder extends ExtendedFileFinder{

    private MenuButton menuButtonTrainingTarget;

    public TrainDataFileFinder(TextField textField, Button button, MenuButton menuButtonTrainingTarget) {
        super(textField, button);
        this.menuButtonTrainingTarget = menuButtonTrainingTarget;
    }

    protected String getTargetLayerId() {
        return menuButtonTrainingTarget.getText();
    }
}
