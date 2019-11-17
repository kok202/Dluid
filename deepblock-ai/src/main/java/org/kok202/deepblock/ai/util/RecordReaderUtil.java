package org.kok202.deepblock.ai.util;

import lombok.Getter;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.collection.CollectionRecordReader;
import org.datavec.api.writable.DoubleWritable;
import org.datavec.api.writable.Writable;
import org.kok202.deepblock.domain.stream.NumericRecordSet;
import org.kok202.deepblock.domain.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RecordReaderUtil {
    public static RecordReader toRecordReader(NumericRecordSet numericRecordSet){
        List<List<Writable>> recordReaderCollection = numericRecordSet
                .getRecords()
                .stream()
                .map(record -> {
                    List<Writable> recordWritable = record.stream()
                            .map(DoubleWritable::new)
                            .collect(Collectors.toList());
                    return recordWritable;
                })
                .collect(Collectors.toList());
        RecordReader recordReader = new CollectionRecordReader(recordReaderCollection);
        return recordReader;
    }

    public static RecordReader createRamdonRecordReader(int recordsSize, int recordSize){
        List<List<Writable>> recordReaderCollection = new ArrayList<>();
        for(int i = 0; i < recordsSize; i++){
            List<Writable> recordWritable = new ArrayList<>();
            for(int j = 0; j < recordSize; j++){
                recordWritable.add(new DoubleWritable(RandomUtil.getDouble()));
            }
            recordReaderCollection.add(recordWritable);
        }
        RecordReader recordReader = new CollectionRecordReader(recordReaderCollection);
        return recordReader;
    }
}
