package org.kok202.dluid.adapter.file;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.kok202.dluid.ai.AIFacade;
import org.kok202.dluid.ai.singleton.structure.ManagedRecordSet;

public class TestFeatureFileFinder extends ExtendedFileFinder {

    public TestFeatureFileFinder(TextField textField, Button button) {
        super(textField, button);
    }

    @Override
    public ManagedRecordSet getManagedRecordSet() {
        return AIFacade.getTestFeatureSet();
    }
}
