package org.kok202.dluid.application.content.design.component;

import javafx.scene.control.Alert;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.application.Util.DialogUtil;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;

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
                .title(AppPropertiesSingleton.getInstance().get("frame.dialog.convolutionNegativeSize.title"))
                .headerText(AppPropertiesSingleton.getInstance().get("frame.dialog.convolutionNegativeSize.header") + outputSizeString)
                .contentText(AppPropertiesSingleton.getInstance().get("frame.dialog.convolutionNegativeSize.content"))
                .build()
                .showAndWait();
    }
}
