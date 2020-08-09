package org.kokzoz.dluid.domain.exception;

public class CanNotFindOutputLayerException extends RuntimeException {
    public CanNotFindOutputLayerException(){
        super("Can not find output layer! One output layer must exist.");
    }
}