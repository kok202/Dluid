package org.kok202.dluid.ai.singleton.structure;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class TrainDataManager {
    private Map<Long, DataSetManager> dataSetManagerMap;

    public TrainDataManager() {
        dataSetManagerMap = new HashMap<>();
    }

    public DataSetManager getDataSetManager(long inputLayerId) {
        DataSetManager dataSetManager = dataSetManagerMap.get(inputLayerId);
        if(dataSetManager == null){
            DataSetManager newDataSetManager = new DataSetManager();
            dataSetManagerMap.put(inputLayerId, newDataSetManager);
            return newDataSetManager;
        }
        return dataSetManager;
    }

    public DataSetManager createDataSetManager(long inputLayerId) {
        return getDataSetManager(inputLayerId);
    }
}
