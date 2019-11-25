package org.kok202.dluid.domain.stream.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.kok202.dluid.domain.stream.NumericConverter;
import org.kok202.dluid.domain.stream.StringRecordSet;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelReader {
    public static final Pattern excelCellPattern = Pattern.compile("^([A-Z]+)([0-9]+)$");
    private int rowOffset; // start from 1
    private int colOffset; // start from 1
    private int rowSize;
    private int colSize;
    private boolean hasHead;

    public ExcelReader(int rowOffset, int colOffset, int rowSize, int colSize, boolean hasHead) {
        this.rowOffset = rowOffset;
        this.colOffset = colOffset;
        this.rowSize = rowSize;
        this.colSize = colSize;
        this.hasHead = hasHead;
    }

    public ExcelReader(String cellStart, String cellEnd, boolean hasHead) {
        this.hasHead = hasHead;
        cellStart = cellStart.toUpperCase();
        cellEnd = cellEnd.toUpperCase();

        Matcher matcherStart = excelCellPattern.matcher(cellStart);
        Matcher matcherEnd = excelCellPattern.matcher(cellEnd);
        if(matcherStart.find()){
            String rowStartString = matcherStart.group(2);
            String colStartString = matcherStart.group(1);
            this.rowOffset = Integer.parseInt(rowStartString);
            this.colOffset = NumericConverter.convertAlphabetToInteger(colStartString);
        }
        if(matcherEnd.find()){
            String rowEndString = matcherEnd.group(2);
            String colEndString = matcherEnd.group(1);
            int rowEnd = Integer.parseInt(rowEndString);
            int colEnd = NumericConverter.convertAlphabetToInteger(colEndString);
            this.rowSize = rowEnd - rowOffset + 1;
            this.colSize = colEnd - colOffset + 1;
            if(this.hasHead)
                this.rowSize--;
        }
    }

    public StringRecordSet read(String filePath) {
        if(filePath.endsWith(".xls"))
            return readFromXLS(filePath);
        if(filePath.endsWith(".xlsx"))
            return readFromXLSX(filePath);
        return null;
    }

    public StringRecordSet readFromXLS(String filePath){
        try(FileInputStream fis = new FileInputStream(filePath);
            Workbook workbook = new HSSFWorkbook(fis)) {
            return readFrom(filePath, workbook);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public StringRecordSet readFromXLSX(String filePath){
        try(FileInputStream fis = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(fis)) {
            return readFrom(filePath, workbook);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private StringRecordSet readFrom(String filePath, Workbook workbook) {
        StringRecordSet stringRecordSet = new StringRecordSet();
        Sheet currentSheet = workbook.getSheetAt(0);

        // Read header
        if(hasHead){
            ArrayList<String> head = new ArrayList<>();
            Row row = currentSheet.getRow(0 + rowOffset - 1);
            for(int colIndex = 0; colIndex < colSize; colIndex++){
                Cell cell = row.getCell(colIndex + colOffset - 1);
                String cellValue = cell.getStringCellValue();
                head.add(cellValue);
            }
            stringRecordSet.setHeader(head);
            rowOffset++;
        }

        // Read records
        ArrayList<ArrayList<String>> records = new ArrayList<>();
        for(int rowIndex = 0; rowIndex < rowSize; rowIndex++){
            ArrayList<String> record = new ArrayList<>();
            Row row = currentSheet.getRow(rowIndex + rowOffset - 1);
            for(int colIndex = 0; colIndex < colSize; colIndex++){
                Cell cell = row.getCell(colIndex + colOffset - 1);
                String cellValue = readCell(cell);
                record.add(cellValue);
            }
            records.add(record);
        }
        stringRecordSet.setRecords(records);
        return stringRecordSet;
    }

    private String readCell(Cell cell){
        String value = "";
        switch (cell.getCellType()){
            case STRING:
                value = "" + cell.getStringCellValue();
                break;
            case NUMERIC:
                value = "" + cell.getNumericCellValue();
                break;
            case FORMULA:
                value = "" + cell.getCellFormula();
                break;
        }
        return value;
    }
}