package org.kok202.deepblock.application.adapter.file;

import org.kok202.deepblock.ai.interfaces.AIInterface;
import org.kok202.deepblock.domain.stream.NumericRecordSet;

public class TestResultImageSaver extends ExtendedImageSaver {
    @Override
    protected NumericRecordSet getNumericRecordSet() {
        return AIInterface.getTestResultSet().getNumericRecordSet();
    }
}
