package org.kok202.deepblock.domain.stream;

import org.kok202.deepblock.domain.stream.excel.ExcelReader;
import org.kok202.deepblock.domain.stream.excel.ExcelWriter;

public class ExcelIO {
        public static void main(String[] args) throws  Exception{
            StringRecordSet stringRecordSet = new StringRecordSet();
            stringRecordSet.setHeader("col1", "col2");
            stringRecordSet.addRecord("hello", "world");
            stringRecordSet.addRecord("good", "bye");
            stringRecordSet.addRecord("123", "456");
            stringRecordSet.addRecord("abc", "def");
            stringRecordSet.addRecord("qwe", "rty");

            String filePath = "C:\\Users\\user\\Downloads\\test.xlsx";
            ExcelWriter excelWriter = new ExcelWriter(4, 3);
            excelWriter.writeAsXLSX(filePath, stringRecordSet);

            filePath = "C:\\Users\\user\\Downloads\\test.xlsx";
            ExcelReader excelReader = new ExcelReader(4,3,5,2,true);
            StringRecordSet readedStringRecordSet = excelReader.readFromXLSX(filePath);
            System.out.println(readedStringRecordSet.toString());
        }
}