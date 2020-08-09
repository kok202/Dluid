package org.kokzoz.dluid.domain.stream;

import lombok.Data;

import java.util.ArrayList;

@Data
public class StringRecordSet {
    private ArrayList<String> header = null;
    private ArrayList<ArrayList<String>> records = new ArrayList<>();

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

    public void addRecord(String... fileds){
        ArrayList<String> record = new ArrayList<>();
        for(String field : fileds){
            record.add(field);
        }
        records.add(record);
    }
}
