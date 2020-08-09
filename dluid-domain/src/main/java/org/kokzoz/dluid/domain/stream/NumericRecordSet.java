package org.kokzoz.dluid.domain.stream;

import lombok.Data;

import java.util.ArrayList;

@Data
public class NumericRecordSet {
    private ArrayList<String> header;
    private ArrayList<ArrayList<Double>> records = new ArrayList<>();

    public boolean hasHeader(){
        return header != null;
    }

    public void setHeader(ArrayList<String> header) {
        this.header = header;
    }

    public void setHeader(String... fileds){
        header = new ArrayList<>();
        for(String field : fileds){
            header.add(field);
        }
    }

    public void addRecord(double... fileds){
        ArrayList<Double> record = new ArrayList<>();
        for(Double field : fileds){
            record.add(field);
        }
        records.add(record);
    }

    public void addRecord(ArrayList<Double> record){
        records.add(record);
    }

    public static NumericRecordSet convertFrom(StringRecordSet stringRecordSet){
        ArrayList<ArrayList<Double>> numericRecords = new ArrayList<>();
        stringRecordSet.getRecords().forEach(record -> {
            ArrayList<Double> numericRecord = new ArrayList<>();
            record.forEach(stringData -> {
                numericRecord.add(Double.parseDouble(stringData));
            });
            numericRecords.add(numericRecord);
        });

        NumericRecordSet numericRecordSet = new NumericRecordSet();
        numericRecordSet.setHeader(stringRecordSet.getHeader());
        numericRecordSet.setRecords(numericRecords);
        return numericRecordSet;
    }

    public StringRecordSet toStringRecordSet(){
        ArrayList<ArrayList<String>> stringRecords = new ArrayList<>();
        getRecords().forEach(record -> {
            ArrayList<String> stringRecord = new ArrayList<>();
            record.forEach(doubleData -> {
                stringRecord.add(String.valueOf(doubleData));
            });
            stringRecords.add(stringRecord);
        });

        StringRecordSet stringRecordSet = new StringRecordSet();
        stringRecordSet.setHeader(getHeader());
        stringRecordSet.setRecords(stringRecords);
        return stringRecordSet;
    }

    public double[] getRecordAsArray(int index){
        double[] recordObjects = records.get(index)
                .stream()
                .mapToDouble(Number::doubleValue)
                .toArray();
        return recordObjects;
    }

    public int getRecordsSize(){
        return getRecords().size();
    }

    public int getRecordSize(){
        if(getRecords() == null || getRecords().isEmpty())
            return 0;
        return getRecords().get(0).size();
    }

    public int getColumnSize(){
        if(header != null)
            return header.size();
        if(records != null)
            return (records.isEmpty())? 0 : records.get(0).size();
        return 0;
    }
}
