package org.kok202.deepblock.ai.singleton.structure;

import lombok.Data;

@Data
public class TestProperty {
    private DataSetManager dataSetManager;

    public TestProperty() {
        dataSetManager = new DataSetManager();
    }
}
