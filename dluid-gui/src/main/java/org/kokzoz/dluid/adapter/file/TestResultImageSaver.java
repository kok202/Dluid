package org.kokzoz.dluid.adapter.file;

import org.kokzoz.dluid.ai.AIFacade;
import org.kokzoz.dluid.domain.stream.NumericRecordSet;

public class TestResultImageSaver extends ExtendedImageSaver {
    @Override
    protected NumericRecordSet getNumericRecordSet() {
        return AIFacade.getTestResultSet().getNumericRecordSet();
    }
}
