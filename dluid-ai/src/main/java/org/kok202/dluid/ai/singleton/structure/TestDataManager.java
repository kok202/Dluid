package org.kok202.dluid.ai.singleton.structure;

import lombok.Getter;

@Getter
public class TestDataManager {
    private DataSetManager dataSetManager;

    public TestDataManager() {
        dataSetManager = new DataSetManager();
    }
}
