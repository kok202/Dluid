package org.kok202.dluid.application.content.design.component;

import javafx.scene.control.Alert;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.application.Util.DialogUtil;

public abstract class AbstractConvolutionLayerComponentController extends AbstractLayerComponentController {
    AbstractConvolutionLayerComponentController(Layer layer) {
        super(layer);
    }

    abstract protected void setTextFieldByLayerProperties();
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
}
