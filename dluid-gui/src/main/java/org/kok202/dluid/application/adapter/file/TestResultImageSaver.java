package org.kok202.dluid.application.adapter.file;

import org.kok202.dluid.ai.AIFacade;
import org.kok202.dluid.domain.stream.NumericRecordSet;

public class TestResultImageSaver extends ExtendedImageSaver {
    @Override
    protected NumericRecordSet getNumericRecordSet() {
        return AIFacade.getTestResultSet().getNumericRecordSet();
    }
}
