package org.kok202.deepblock.ai.util;

import org.datavec.api.records.reader.RecordReader;
import org.deeplearning4j.datasets.datavec.RecordReaderMultiDataSetIterator;
import org.kok202.deepblock.ai.facade.AIConstant;
import org.kok202.deepblock.ai.singleton.AIPropertiesSingleton;
import org.kok202.deepblock.domain.stream.NumericRecordSet;
import org.nd4j.linalg.dataset.api.iterator.MultiDataSetIterator;

public class MultiDataSetIteratorUtil {
    public static MultiDataSetIterator toMultiDataSetIterator(NumericRecordSet featureDataSet, NumericRecordSet resultDataSet){
        RecordReader trainFeatures = RecordReaderUtil.toRecordReader(featureDataSet);
        RecordReader trainResults = RecordReaderUtil.toRecordReader(resultDataSet);
        int batchSize = AIPropertiesSingleton
                .getInstance()
                .getTrainProperty()
                .getBatchSize();
        MultiDataSetIterator multiDataSetIterator = new RecordReaderMultiDataSetIterator.Builder(batchSize)
                .addReader(AIConstant.INPUT_READER_NAME, trainFeatures)
                .addReader(AIConstant.OUTPUT_READER_NAME, trainResults)
                .addInput(AIConstant.INPUT_READER_NAME)
                .addOutput(AIConstant.OUTPUT_READER_NAME)
                .build();
        return multiDataSetIterator;
    }
}
