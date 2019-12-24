package org.kok202.dluid.application.content.design.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.kok202.dluid.AppConstant;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;
import org.kok202.dluid.application.util.TextFieldUtil;

public class ComponentSwitchParamController extends AbstractLayerComponentController {

    @FXML private Label labelWidth;
    @FXML private Label labelHeight;
    @FXML private Label labelInputOutput;

    @FXML private TextField textFieldInputOutputX;
    @FXML private TextField textFieldInputOutputY;

    public ComponentSwitchParamController(Layer layer) {
        super(layer);
    }

    @Override
    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/design/component/switch_param.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        TextFieldUtil.applyPositiveIntegerFilter(textFieldInputOutputX, AppConstant.DEFAULT_INPUT_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldInputOutputY, AppConstant.DEFAULT_INPUT_SIZE);
        setTextFieldByLayerProperties();

        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.title"));
        labelWidth.setText(AppPropertiesSingleton.getInstance().get("frame.component.2d.width"));
        labelHeight.setText(AppPropertiesSingleton.getInstance().get("frame.component.2d.height"));
        labelInputOutput.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.inputOutputSize"));
    }

    private void setTextFieldByLayerProperties(){
        detachTextChangedListener(textFieldInputOutputX, textFieldInputOutputY);
        textFieldInputOutputX.setText(String.valueOf(layer.getProperties().getInputSize()[0]));
        textFieldInputOutputY.setText(String.valueOf(layer.getProperties().getInputSize()[1]));
        attachTextChangedListener(textFieldInputOutputX, textFieldInputOutputY);
    }

    @Override
    protected void textFieldChangedHandler(){
        layer.getProperties().setInputSize(
                TextFieldUtil.parseInteger(textFieldInputOutputX),
                TextFieldUtil.parseInteger(textFieldInputOutputY));
        layer.getProperties().setOutputSize(
                TextFieldUtil.parseInteger(textFieldInputOutputX),
                TextFieldUtil.parseInteger(textFieldInputOutputY));
        notifyLayerDataChanged();
    }
}
