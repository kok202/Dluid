package org.kok202.deepblock.ai.singleton.structure;

import lombok.Data;
import org.kok202.deepblock.ai.helper.DataSetConverter;
import org.nd4j.linalg.dataset.DataSet;

@Data
public class DataSetManager {
    private ManagedRecordSet managedFeatureRecordSet;
    private ManagedRecordSet managedResultRecordSet;

    public DataSetManager() {
        managedFeatureRecordSet = new ManagedRecordSet();
        managedResultRecordSet = new ManagedRecordSet();
    }

    public DataSet converToDataSet(){
        DataSetConverter dataSetConverter = new DataSetConverter(managedFeatureRecordSet.getNumericRecordSet(), managedResultRecordSet.getNumericRecordSet());
        return dataSetConverter.toDataSet();
    }
}
