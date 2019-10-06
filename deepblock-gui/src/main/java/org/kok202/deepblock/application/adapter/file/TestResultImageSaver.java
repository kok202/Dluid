package org.kok202.deepblock.application.adapter.file;

import org.kok202.deepblock.ai.global.AIPropertiesSingleton;
import org.kok202.deepblock.domain.stream.NumericRecordSet;

public class TestResultImageSaver extends ExtendedImageSaver {
    @Override
    protected NumericRecordSet getNumericRecordSet() {
        return AIPropertiesSingleton.getInstance()
                .getTestProperty()
                .getDataSetManager()
                .getManagedResultRecordSet()
                .getNumericRecordSet();
    }
}
