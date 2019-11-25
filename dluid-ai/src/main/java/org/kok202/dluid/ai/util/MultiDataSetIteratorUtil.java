package org.kok202.dluid.ai.util;

import org.datavec.api.records.reader.RecordReader;
import org.deeplearning4j.datasets.datavec.RecordReaderMultiDataSetIterator;
import org.kok202.dluid.ai.AIConstant;
import org.kok202.dluid.domain.stream.NumericRecordSet;
import org.nd4j.linalg.dataset.api.iterator.MultiDataSetIterator;

public class MultiDataSetIteratorUtil {
    public static MultiDataSetIterator toMultiDataSetIterator(int batchSize, NumericRecordSet featureDataSet, NumericRecordSet resultDataSet){
        RecordReader trainFeatures = RecordReaderUtil.toRecordReader(featureDataSet);
        RecordReader trainResults = RecordReaderUtil.toRecordReader(resultDataSet);
        MultiDataSetIterator multiDataSetIterator = new RecordReaderMultiDataSetIterator.Builder(batchSize)
                .addReader(AIConstant.INPUT_READER_NAME, trainFeatures)
                .addReader(AIConstant.OUTPUT_READER_NAME, trainResults)
                .addInput(AIConstant.INPUT_READER_NAME, 0, featureDataSet.getRecordSize() - 1)
                .addOutput(AIConstant.OUTPUT_READER_NAME, 0, resultDataSet.getRecordSize() - 1)
                .build();
        return multiDataSetIterator;
    }
}
