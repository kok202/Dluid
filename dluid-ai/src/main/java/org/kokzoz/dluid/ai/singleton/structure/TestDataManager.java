package org.kokzoz.dluid.ai.singleton.structure;

import lombok.Getter;

@Getter
public class TestDataManager {
    private DataSetManager dataSetManager;

    public TestDataManager() {
        dataSetManager = new DataSetManager();
    }
}
