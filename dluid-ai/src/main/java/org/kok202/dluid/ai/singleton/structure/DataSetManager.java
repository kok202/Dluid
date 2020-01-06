package org.kok202.dluid.ai.singleton.structure;

import lombok.Data;

@Data
public class DataSetManager {
    private String inputLayerId;
    private ManagedRecordSet managedFeatureRecordSet;
    private ManagedRecordSet managedResultRecordSet;

    public DataSetManager() {
        managedFeatureRecordSet = new ManagedRecordSet();
        managedResultRecordSet = new ManagedRecordSet();
    }
}
