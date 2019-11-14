package org.kok202.deepblock.ai.util;

import lombok.Getter;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.collection.CollectionRecordReader;
import org.datavec.api.writable.NDArrayWritable;
import org.datavec.api.writable.Writable;
import org.kok202.deepblock.domain.stream.NumericRecordSet;
import org.kok202.deepblock.domain.util.ListUtil;
import org.nd4j.linalg.factory.Nd4j;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RecordReaderUtil {
    public static RecordReader toRecordReader(NumericRecordSet numericRecordSet){
        List<List<Writable>> recordReaderCollection = numericRecordSet
                .getRecords()
                .stream()
                .map(record -> {
                    double[] values = ListUtil.toDoubleArray(record);
                    return Collections.<Writable>singletonList(new NDArrayWritable(Nd4j.create(values)));
                })
                .collect(Collectors.toList());
        RecordReader recordReader = new CollectionRecordReader(recordReaderCollection);
        return recordReader;
    }
}
