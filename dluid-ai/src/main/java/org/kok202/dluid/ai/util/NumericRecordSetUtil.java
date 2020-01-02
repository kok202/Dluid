package org.kok202.dluid.ai.util;

import lombok.Getter;
import org.kok202.dluid.domain.stream.NumericRecordSet;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;

@Getter
public class NumericRecordSetUtil {

    public static DataSet convertAsDataSet(NumericRecordSet featureRecordSet, NumericRecordSet resultRecordSet){
        INDArray featureSet = convertAsINDArray(featureRecordSet);
        INDArray resultSet = convertAsINDArray(resultRecordSet);
        return new DataSet(featureSet, resultSet);
    }

    public static INDArray convertAsINDArray(NumericRecordSet dataSet){
        int rowMax = dataSet.getRecords().size();
        int colMax = dataSet.getRecords().get(0).size();
        INDArray resultINDArray = Nd4j.zeros(rowMax, colMax);
        for(int row = 0; row < rowMax; row++){
            for(int col = 0; col < colMax; col++){
                double value = dataSet.getRecords().get(row).get(col);
                resultINDArray.putScalar(new int[]{row, col}, value);
            }
        }
        return resultINDArray;
    }

    public static NumericRecordSet convertAsNumericRecordSet(INDArray indArray){
        NumericRecordSet numericRecordSet = new NumericRecordSet();
        for(int i = 0; i < indArray.length(); i++){
            INDArray record = indArray.getRow(i);
            numericRecordSet.addRecord(record.toDoubleVector());
        }
        return numericRecordSet;
    }

}