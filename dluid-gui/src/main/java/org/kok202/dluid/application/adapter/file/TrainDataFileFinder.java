package org.kok202.dluid.application.adapter.file;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.kok202.dluid.application.Util.TextFieldUtil;

public abstract class TrainDataFileFinder extends ExtendedFileFinder{

    private TextField textFieldTrainingTarget;

    public TrainDataFileFinder(TextField textField, Button button, TextField textFieldTrainingTarget) {
        super(textField, button);
        this.textFieldTrainingTarget = textFieldTrainingTarget;
    }

    protected long getTargetLayerId() {
        return TextFieldUtil.parseLong(textFieldTrainingTarget);
    }
}
