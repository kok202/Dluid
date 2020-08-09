package org.kokzoz.dluid.adapter.file;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.kokzoz.dluid.ai.AIFacade;
import org.kokzoz.dluid.ai.singleton.structure.ManagedRecordSet;

public class TestFeatureFileFinder extends ExtendedFileFinder {

    public TestFeatureFileFinder(TextField textField, Button button) {
        super(textField, button);
    }

    @Override
    public ManagedRecordSet getManagedRecordSet() {
        return AIFacade.getTestFeatureSet();
    }
}
