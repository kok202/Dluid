package org.kok202.dluid.adapter;

import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;

import java.util.ArrayList;

public class EditableTableCell extends TextFieldTableCell<ArrayList<Double>, Double> {
    public EditableTableCell() {
        setConverter(new StringConverter<Double>(){
            @Override
            public Double fromString (String value){
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
            public String toString (Double value){
                if (value == null) {
                    return "";
                }
                return String.format("%f", value);
            }
        });
    }
}