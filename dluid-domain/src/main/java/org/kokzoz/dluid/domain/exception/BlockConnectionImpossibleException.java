package org.kokzoz.dluid.domain.exception;

public class BlockConnectionImpossibleException extends RuntimeException {
    public BlockConnectionImpossibleException(){
        super("Connected block already exist.");
    }
}
