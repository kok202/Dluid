package org.kokzoz.dluid.ai.util;

import lombok.Getter;
import org.kokzoz.dluid.domain.exception.FeatureSetResultSetUnmatchedException;
import org.kokzoz.dluid.domain.stream.NumericRecordSet;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class NumericRecordSetUtil {

    public static DataSet shuffleAndConvertAsDataSet(NumericRecordSet featureRecordSet, NumericRecordSet resultRecordSet){
        int featureRowMax = featureRecordSet.getRecords().size();
        int resultRowMax = resultRecordSet.getRecords().size();
        if(featureRowMax != resultRowMax)
            throw new FeatureSetResultSetUnmatchedException(featureRowMax, resultRowMax);

        List<Integer> randomSequences = IntStream.range(0, featureRowMax).boxed().collect(Collectors.toList());
        Collections.shuffle(randomSequences);

        final int featureSetColMax = featureRecordSet.getRecords().get(0).size();
        final int resultSetColMax = resultRecordSet.getRecords().get(0).size();
        INDArray featureSet = Nd4j.zeros(featureRowMax, featureSetColMax);
        INDArray resultSet = Nd4j.zeros(resultRowMax, resultSetColMax);
        for (int i = 0; i < randomSequences.size(); i++) {
            int row = randomSequences.get(i);
            for (int col = 0; col < featureSetColMax; col++) {
                double value = featureRecordSet.getRecords().get(row).get(col);
                featureSet.putScalar(new int[]{i, col}, value);
            }
            for (int col = 0; col < resultSetColMax; col++) {
                double value = resultRecordSet.getRecords().get(row).get(col);
                resultSet.putScalar(new int[]{i, col}, value);
            }
        }
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
        int rowsLength = indArray.rows();
        for(int i = 0; i < rowsLength; i++){
            INDArray record = indArray.getRow(i);
            numericRecordSet.addRecord(record.toDoubleVector());
        }
        return numericRecordSet;
    }

}