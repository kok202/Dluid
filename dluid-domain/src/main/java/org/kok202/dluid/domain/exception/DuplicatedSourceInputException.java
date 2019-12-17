package org.kok202.dluid.domain.exception;

public class DuplicatedSourceInputException extends RuntimeException {
    public DuplicatedSourceInputException(){
        super("Source input is duplicated");
    }
}
