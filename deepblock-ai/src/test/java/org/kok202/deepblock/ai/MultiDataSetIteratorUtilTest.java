package org.kok202.deepblock.ai;

import org.junit.Test;
import org.kok202.deepblock.ai.util.MultiDataSetIteratorUtil;
import org.kok202.deepblock.domain.stream.NumericRecordSet;
import org.kok202.deepblock.domain.stream.StringRecordSet;
import org.nd4j.linalg.dataset.api.MultiDataSet;
import org.nd4j.linalg.dataset.api.iterator.MultiDataSetIterator;

public class MultiDataSetIteratorUtilTest {
    @Test
    public void test(){
        StringRecordSet featureSet = new StringRecordSet();
        featureSet.setHeader("a", "b");
        featureSet.addRecord("0", "0");
        featureSet.addRecord("0", "1");
        featureSet.addRecord("1", "0");
        featureSet.addRecord("1", "1");
        StringRecordSet resultSet = new StringRecordSet();
        resultSet.setHeader("xor(a,b)");
        resultSet.addRecord("0");
        resultSet.addRecord("1");
        resultSet.addRecord("1");
        resultSet.addRecord("0");
        NumericRecordSet trainFeature = NumericRecordSet.convertFrom(featureSet);
        NumericRecordSet trainResult = NumericRecordSet.convertFrom(resultSet);
        int batchSize = 4;
        MultiDataSetIterator multiDataSetIterator = MultiDataSetIteratorUtil.toMultiDataSetIterator(batchSize, trainFeature, trainResult);
        System.out.println("start");
        while(multiDataSetIterator.hasNext()){
            MultiDataSet multiDataSet = multiDataSetIterator.next();
            System.out.println(multiDataSet);
        }
        System.out.println("done");
    }
}
