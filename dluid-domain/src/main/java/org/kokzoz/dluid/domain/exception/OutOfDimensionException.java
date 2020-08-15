package org.kokzoz.dluid.domain.exception;

import lombok.Getter;

import java.util.Arrays;

@Getter
public class OutOfDimensionException extends RuntimeException {

    public OutOfDimensionException(int dimension, String target) {
        super(String.format("Setting at %s is not acceptable. because it is %d dimension.", target, dimension));
    }

}
