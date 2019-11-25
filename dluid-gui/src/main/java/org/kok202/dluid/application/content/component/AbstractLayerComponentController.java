package org.kok202.dluid.application.content.component;

import javafx.beans.InvalidationListener;
import javafx.scene.control.TextField;
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
        CanvasFacade.notifyLayerDataChanged(layer.getId());
    }
}
