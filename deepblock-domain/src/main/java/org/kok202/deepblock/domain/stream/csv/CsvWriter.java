package org.kok202.deepblock.domain.stream.csv;

import com.opencsv.CSVWriter;
import com.opencsv.ICSVWriter;
import org.kok202.deepblock.domain.stream.StringRecordSet;

import java.io.FileWriter;
import java.util.Arrays;

public class CsvWriter {

    public void write(String filePath, StringRecordSet stringRecordSet){
        try (CSVWriter csvWriter = new CSVWriter(
                    new FileWriter(filePath),
                    ICSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END)){

            if(stringRecordSet.hasHeader()) {
                Object[] buffer = stringRecordSet.getHeader().toArray();
                String[] header = Arrays.copyOf(buffer, buffer.length, String[].class);
                String[] headerWithQoutes = new String[buffer.length];
                for(int i = 0; i < header.length; i++){
                    headerWithQoutes[i] = header[i].replace(',', ' ');
                }
                csvWriter.writeNext(headerWithQoutes);
            }

            stringRecordSet.getRecords().forEach(record -> {
                Object[] buffer = record.toArray();
                String[] recordString = Arrays.copyOf(buffer, buffer.length, String[].class);
                csvWriter.writeNext(recordString);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
