package org.kok202.deepblock.application.content.component;

import javafx.beans.InvalidationListener;
import javafx.scene.control.TextField;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.canvas.interfaces.CanvasInterface;

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
        CanvasInterface.notifyLayerDataChanged(layer.getId());
    }
}
