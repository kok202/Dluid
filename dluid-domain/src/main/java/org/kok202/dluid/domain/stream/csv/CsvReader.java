package org.kok202.dluid.domain.stream.csv;

import com.opencsv.CSVReader;
import org.kok202.dluid.domain.stream.StringRecordSet;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

public class CsvReader {
    private int colStart; // start from 1
    private int colEnd; // start from 1
    private boolean hasHead;

    public CsvReader(int colStart, int colEnd, boolean hasHead) {
        this.colStart = colStart;
        this.colEnd = colEnd;
        this.hasHead = hasHead;
    }

    public StringRecordSet read(String filePath){
        StringRecordSet stringRecordSet = new StringRecordSet();
        try(FileInputStream fis = new FileInputStream(filePath);
            InputStreamReader is = new InputStreamReader(fis, "UTF-8")){
            CSVReader reader = new CSVReader(is);
            List<String[]> list = reader.readAll();

            if(hasHead){
                stringRecordSet.setHeader(subList(list.get(0), colStart, colEnd));

                list.subList(1,list.size()).forEach(record -> {
                    String[] stringArrays = subList(record, colStart, colEnd);
                    stringRecordSet.addRecord(stringArrays);
                });
            }
            else{
                list.forEach(record -> {
                    String[] stringArrays = subList(record, colStart, colEnd);
                    stringRecordSet.addRecord(stringArrays);
                });
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return stringRecordSet;
    }

    private static String[] subList(String[] origin, int start, int end){
        String[] result = new String[end - start + 1];
        for(int originIndex = start; originIndex <= end; originIndex++){
            result[originIndex - start] = origin[originIndex - 1];
        }
        return result;
    }
}
