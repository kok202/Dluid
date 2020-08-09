package org.kokzoz.dluid.domain.exception;

public class ExcelIllegalDataFormatException extends RuntimeException {
    public ExcelIllegalDataFormatException(){
        super("Excel data is illegal data format.");
    }
}
