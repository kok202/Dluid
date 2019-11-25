package org.kok202.dluid.domain.stream.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.kok202.dluid.domain.stream.NumericRecordSet;
import org.kok202.dluid.domain.stream.StringRecordSet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExcelWriter {
    private int rowOffset; // start from 1
    private int colOffset; // start from 1

    public ExcelWriter() {
        this.rowOffset = 1;
        this.colOffset = 1;
    }

    public ExcelWriter(int rowOffset, int colOffset) {
        this.rowOffset = rowOffset;
        this.colOffset = colOffset;
    }

    public void writeAsXLS(String filePath, StringRecordSet stringRecordSet) {
        writeAs(filePath, stringRecordSet, new HSSFWorkbook());
    }

    public void writeAsXLS(String filePath, NumericRecordSet numericRecordSet) {
        writeAs(filePath, numericRecordSet, new HSSFWorkbook());
    }

    public void writeAsXLSX(String filePath, StringRecordSet stringRecordSet) {
        writeAs(filePath, stringRecordSet, new XSSFWorkbook());
    }

    public void writeAsXLSX(String filePath, NumericRecordSet numericRecordSet) {
        writeAs(filePath, numericRecordSet, new XSSFWorkbook());
    }

    private void writeAs(String filePath, NumericRecordSet numericRecordSet, Workbook workbook) {
        if(numericRecordSet == null)
            return;

        // Write headers
        Sheet worksheet = workbook.createSheet();
        boolean headExist = numericRecordSet.hasHeader();
        if(headExist){
            Row row = worksheet.createRow(0 + rowOffset - 1);
            ArrayList<String> head = numericRecordSet.getHeader();
            for(int colIndex = 0; colIndex < head.size(); colIndex++){
                String field = head.get(colIndex);
                Cell cell = row.createCell(colIndex + colOffset - 1);
                cell.setCellValue(field);
            }
            rowOffset++;
        }

        // Write records
        ArrayList<ArrayList<Double>> records = numericRecordSet.getRecords();
        for(int rowIndex = 0; rowIndex < records.size(); rowIndex++){
            Row row = worksheet.createRow(rowIndex + rowOffset - 1);
            ArrayList<Double> record = records.get(rowIndex);
            System.out.println(rowIndex);
            for(int colIndex = 0; colIndex < record.size(); colIndex++){
                Double field = record.get(colIndex);
                Cell cell = row.createCell(colIndex + colOffset - 1);
                cell.setCellValue(field);
            }
        }

        write(filePath,workbook);
    }

    private void writeAs(String filePath, StringRecordSet stringRecordSet, Workbook workbook) {
        if(stringRecordSet == null)
            return;

        // Write headers
        Sheet worksheet = workbook.createSheet();
        boolean headExist = stringRecordSet.hasHeader();
        if(headExist){
            Row row = worksheet.createRow(0 + rowOffset - 1);
            ArrayList<String> head = stringRecordSet.getHeader();
            for(int colIndex = 0; colIndex < head.size(); colIndex++){
                String field = head.get(colIndex);
                Cell cell = row.createCell(colIndex + colOffset - 1);
                cell.setCellValue(field);
            }
            rowOffset++;
        }

        // Write records
        ArrayList<ArrayList<String>> records = stringRecordSet.getRecords();
        for(int rowIndex = 0; rowIndex < records.size(); rowIndex++){
            Row row = worksheet.createRow(rowIndex + rowOffset - 1);
            ArrayList<String> record = records.get(rowIndex);
            for(int colIndex = 0; colIndex < record.size(); colIndex++){
                String field = record.get(colIndex);
                Cell cell = row.createCell(colIndex + colOffset - 1);
                cell.setCellValue(field);
            }
        }

        write(filePath,workbook);
    }

    public void write(String filePath, Workbook workbook){
        File file = new File(filePath);
        try(FileOutputStream fos = new FileOutputStream(file)) {
            workbook.write(fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}