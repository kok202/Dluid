package org.kokzoz.dluid.content.design.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.kokzoz.dluid.domain.entity.Layer;
import org.kokzoz.dluid.singleton.AppPropertiesSingleton;
import org.kokzoz.dluid.util.TextFieldUtil;

public class ComponentInputParamController extends AbstractLayerComponentController {

    @FXML private Label labelInputOutputX;
    @FXML private Label labelInputSize;
    @FXML private Label labelOutputSize;

    @FXML private TextField textFieldInputSizeX;
    @FXML private TextField textFieldOutputSizeX;

    public ComponentInputParamController(Layer layer) {
        super(layer);
    }

    @Override
    public AnchorPane createView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/design/component/input_param.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        TextFieldUtil.applyPositiveIntegerFilter(textFieldInputSizeX, 1);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldOutputSizeX, 1);
        setTextFieldByLayerProperties();
    }

    protected void setTextFieldByLayerProperties() {
        textFieldInputSizeX.setText(String.valueOf(layer.getProperties().getInput().getVolume()));
        textFieldOutputSizeX.setText(String.valueOf(layer.getProperties().getOutput().getVolume()));
        attachTextChangedListener(textFieldInputSizeX);

        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.title"));
        labelInputOutputX.setText(AppPropertiesSingleton.getInstance().get("frame.component.width"));
        labelInputSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.inputSize"));
        labelOutputSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.outputSize"));
    }

    @Override
    protected void textFieldChangedHandler() {
        int value = TextFieldUtil.parseInteger(textFieldInputSizeX, 1);
        layer.getProperties().getInput().setX(value);
        layer.getProperties().getOutput().setX(value);
        textFieldOutputSizeX.setText(String.valueOf(value));
        reshapeBlock();
    }
}
