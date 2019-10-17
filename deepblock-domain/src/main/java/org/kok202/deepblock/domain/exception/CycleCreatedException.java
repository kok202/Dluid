package org.kok202.deepblock.domain.exception;

public class CycleCreatedException extends RuntimeException {
    public CycleCreatedException(){
        super("Tree has cycle. Node having more than two parents is unacceptable.");
    }
}