package org.kok202.deepblock.domain.exception;

public class CycleCreatedException extends RuntimeException {
    public CycleCreatedException(){
        super("Graph has cycle. Node having more than two parents is unacceptable.");
    }
}