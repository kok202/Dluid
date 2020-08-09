package org.kokzoz.dluid.domain.exception;

import lombok.Getter;

@Getter
public class InvalidParameterException extends RuntimeException {

    private String message;

    public InvalidParameterException(String message) {
        super(message);
        this.message = message;
    }
}
