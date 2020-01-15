package org.kok202.dluid.application.content.design.component;

import javafx.beans.InvalidationListener;
import javafx.scene.control.TextField;
import org.kok202.dluid.CanvasConstant;
import org.kok202.dluid.CanvasFacade;
import org.kok202.dluid.ai.entity.Layer;

public abstract class AbstractLayerComponentController extends AbstractComponentController {
    private InvalidationListener changeListener = (changeListener) -> textFieldChangedHandler();

    protected Layer layer;

    protected AbstractLayerComponentController(Layer layer) {
        this.layer = layer;
    }

    protected void textFieldChangedHandler(){}

    void detachTextChangedListener(TextField... textFields){
        for(TextField textField : textFields)
            textField.textProperty().removeListener(changeListener);
    }

    void attachTextChangedListener(TextField... textFields){
        for(TextField textField : textFields)
            textField.textProperty().addListener(changeListener);
    }

    protected void notifyLayerDataChanged(){
        double height = CanvasConstant.NODE_DEFAULT_HEIGHT;
        if(layer.getProperties().isChannelExist()){
            if(layer.getProperties().getOutputDimension() == 2)
                height = CanvasConstant.NODE_DEFAULT_HEIGHT * layer.getProperties().getOutputSize()[1];
            else if(layer.getProperties().getOutputDimension() == 3)
                height = CanvasConstant.NODE_DEFAULT_HEIGHT * layer.getProperties().getOutputSize()[2];
        }
        CanvasFacade.findGraphNodeByLayerId(layer.getId()).getData().getBlockInfo().setHeight(height);
        CanvasFacade.notifyLayerDataChanged(layer.getId());
    }
}
