package org.kokzoz.dluid.util;


import javafx.scene.control.TextField;
import org.apache.commons.lang3.StringUtils;

public class TextFieldUtil {
    public static void applyPositiveWithZeroIntegerFilter(TextField textField, int defaultValue){
        textField.setText(String.valueOf(defaultValue));
        textField.textProperty().addListener((observable, oldValue, newValue) ->{
            if (!validatePositiveWithZeroInteger(newValue))
                textField.setText(String.valueOf(defaultValue));
        });
    }

    public static void applyPositiveIntegerFilter(TextField textField, int defaultValue){
        textField.setText(String.valueOf(defaultValue));
        textField.textProperty().addListener((observable, oldValue, newValue) ->{
            if (!validatePositiveInteger(newValue))
                textField.setText(String.valueOf(defaultValue));
        });
    }

    public static void applyPositiveIntegerFilter(int defaultValue, TextField... textFields){
        for (TextField textField : textFields) {
            applyPositiveIntegerFilter(textField, defaultValue);
        }
    }

    public static void applyPositiveLongFilter(TextField textField, long defaultValue){
        textField.setText(String.valueOf(defaultValue));
        textField.textProperty().addListener((observable, oldValue, newValue) ->{
            if (!validatePositiveLong(newValue))
                textField.setText(String.valueOf(defaultValue));
        });
    }

    public static void applyPositiveDoubleFilter(TextField textField, double defaultValue){
        textField.setText(String.valueOf(defaultValue));
        textField.textProperty().addListener((observable, oldValue, newValue) ->{
            if (!validateDouble(newValue))
                textField.setText(String.valueOf(defaultValue));
        });
    }

    public static int parseInteger(TextField textField){
        String targetText = textField.getText();
        if(StringUtils.isEmpty(targetText))
            return 0;
        return Integer.parseInt(targetText);
    }

    public static long parseLong(TextField textField){
        String targetText = textField.getText();
        if(StringUtils.isEmpty(targetText))
            return 0;
        return Long.parseLong(targetText);
    }

    public static double parseDouble(TextField textField){
        String targetText = textField.getText();
        if(StringUtils.isEmpty(targetText))
            return 0;
        return Double.parseDouble(targetText);
    }

    public static int parseInteger(TextField textField, int defaultValue){
        String targetText = textField.getText();
        if(StringUtils.isEmpty(targetText) || !validatePositiveInteger(targetText))
            return defaultValue;
        return Integer.parseInt(targetText);
    }

    public static double parseDouble(TextField textField, double defaultValue){
        String targetText = textField.getText();
        if(StringUtils.isEmpty(targetText) || !validateDouble(targetText))
            return defaultValue;
        return Double.parseDouble(targetText);
    }

    public static boolean validateDouble(String text){
        if(text == null || text.isEmpty())
            return false;
        return text.matches("^[0-9]*.[0-9]*$");
    }

    public static boolean validatePositiveInteger(String text){
        if(text == null || text.isEmpty())
            return false;
        return text.matches("^[1-9][0-9]*$");
    }

    public static boolean validatePositiveLong(String text){
        if(text == null || text.isEmpty())
            return false;
        return text.matches("^[1-9][0-9]*$");
    }

    public static boolean validatePositiveWithZeroInteger(String text){
        if(text == null || text.isEmpty())
            return false;
        return text.matches("^[0-9]+$");
    }

    public static boolean validateExcelCell(String text){
        if(text == null || text.isEmpty())
            return false;
        return text.toUpperCase().matches("^([A-Z]+)([0-9]+)$");
    }
}
