package org.kok202.deepblock.application.content.component;

import javafx.beans.InvalidationListener;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.application.Util.DialogUtil;

public abstract class AbstractConvolutionLayerComponentController extends AbstractLayerComponentController {
    private InvalidationListener changeListener = (changeListener) -> textFieldChangedHandler();
    AbstractConvolutionLayerComponentController(Layer layer) {
        super(layer);
    }

    abstract protected void setTextFieldByLayerProperties();
    abstract protected void textFieldChangedHandler();
    abstract protected int[] getOutputSize();

    void showOutputSizeErrorDialog(int[] outputSize){
        String outputSizeString = "";
        for(int outputSizeElement : outputSize){
            outputSizeString += " " + outputSizeElement;
        }
        DialogUtil.builder()
                .alertType(Alert.AlertType.ERROR)
                .title("Negative output size!")
                .headerText("Convolution output size can not be negative or zero value. : (" + outputSizeString + ")")
                .contentText("Please make output size positive.")
                .build()
                .showAndWait();
    }

    void detachTextChangedListener(TextField... textFields){
        for(TextField textField : textFields)
            textField.textProperty().removeListener(changeListener);
    }

    void applyTextChangedListener(TextField... textFields){
        for(TextField textField : textFields)
            textField.textProperty().addListener(changeListener);
    }
}
