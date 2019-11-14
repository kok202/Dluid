package org.kok202.deepblock.ai.singleton.structure;

import lombok.Data;

@Data
public class DataSetManager {
    private ManagedRecordSet managedFeatureRecordSet;
    private ManagedRecordSet managedResultRecordSet;

    public DataSetManager() {
        managedFeatureRecordSet = new ManagedRecordSet();
        managedResultRecordSet = new ManagedRecordSet();
    }
}
