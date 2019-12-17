package org.kok202.dluid.domain.exception;

import lombok.Getter;

@Getter
public class ParameterUnsetException extends Exception {

    private String parameter;

    public ParameterUnsetException(String parameter) {
        super("Paramater " + parameter + " is not set.");
        this.parameter = parameter;
    }
}
