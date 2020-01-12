package org.kok202.dluid.application.adapter;

import javafx.util.StringConverter;

public class NumericTableCellConverter extends StringConverter<Double> {
    @Override
    public Double fromString(String value) {
        if (value == null) {
            return null;
        }

        value = value.trim();
        if (value.length() < 1) {
            return null;
        }

        return Double.valueOf(value);
    }

    @Override
    public String toString(Double value) {
        if (value == null) {
            return "";
        }
        return String.format ("%f", value);
    }
}
