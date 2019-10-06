package org.kok202.deepblock.domain.stream;

import org.kok202.deepblock.domain.stream.csv.CsvReader;
import org.kok202.deepblock.domain.stream.csv.CsvWriter;

public class CsvIO {
        public static void main(String[] args) throws  Exception{
            StringRecordSet stringRecordSet = new StringRecordSet();
            stringRecordSet.setHeader("col1", "col2");
            stringRecordSet.addRecord("0.01", "0.1");
            stringRecordSet.addRecord("0.02", "0.2");
            stringRecordSet.addRecord("0.03", "0.3");
            stringRecordSet.addRecord("0.04", "0.4");
            stringRecordSet.addRecord("0.05", "0.5");

            String filePath = "C:\\Users\\user\\Downloads\\test.csv";
            CsvWriter csvWriter = new CsvWriter();
            csvWriter.write(filePath, stringRecordSet);

            CsvReader csvReader = new CsvReader(1, 2, true);
            StringRecordSet readedStringRecordSet = csvReader.read(filePath);
            System.out.println(readedStringRecordSet.toString());
        }
}