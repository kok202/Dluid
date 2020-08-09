package org.kokzoz.dluid.domain.exception;

public class ExcelPositionOutOfTableException extends RuntimeException {
    public ExcelPositionOutOfTableException(){
        super("Excel data is out of table.");
    }
}
