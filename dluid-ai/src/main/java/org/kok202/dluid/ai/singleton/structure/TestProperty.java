package org.kok202.dluid.ai.singleton.structure;

import lombok.Data;

@Data
public class TestProperty {
    private DataSetManager dataSetManager;

    public TestProperty() {
        dataSetManager = new DataSetManager();
    }
}
