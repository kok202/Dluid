package org.kok202.dluid.application.content.design.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;
import org.kok202.dluid.application.util.TextFieldUtil;
import org.kok202.dluid.domain.entity.Layer;

public class ComponentFeedforwardParamController extends AbstractLayerComponentController {

    @FXML private Label labelInputOutputX;
    @FXML private Label labelInputSize;
    @FXML private Label labelOutputSize;

    @FXML private TextField textFieldInputSize;
    @FXML private TextField textFieldOutputSize;

    public ComponentFeedforwardParamController(Layer layer) {
        super(layer);
    }

    @Override
    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/design/component/feedforward_param.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        TextFieldUtil.applyPositiveIntegerFilter(textFieldInputSize, 1);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldOutputSize, 1);
        setTextFieldByLayerProperties();
    }

    protected void setTextFieldByLayerProperties(){
        textFieldInputSize.setText(String.valueOf(layer.getProperties().getInputVolume()));
        textFieldOutputSize.setText(String.valueOf(layer.getProperties().getOutputVolume()));
        attachTextChangedListener(textFieldInputSize, textFieldOutputSize);

        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.title"));
        labelInputOutputX.setText(AppPropertiesSingleton.getInstance().get("frame.component.width"));
        labelInputSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.inputSize"));
        labelOutputSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.outputSize"));
    }

    @Override
    protected void textFieldChangedHandler(){
        changeInputSize();
        changeOutputSize();
        notifyLayerDataChanged();
    }

    private void changeInputSize(){
        int value = TextFieldUtil.parseInteger(textFieldInputSize, 1);
        layer.getProperties().setInputSize(value);
    }

    private void changeOutputSize(){
        int value = TextFieldUtil.parseInteger(textFieldOutputSize, 1);
        layer.getProperties().setOutputSize(value);
    }
}
