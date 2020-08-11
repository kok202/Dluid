package org.kokzoz.dluid.content.design.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.kokzoz.dluid.domain.entity.Layer;
import org.kokzoz.dluid.singleton.AppPropertiesSingleton;
import org.kokzoz.dluid.util.TextFieldUtil;

public class ComponentRecurrentParamController extends AbstractLayerComponentController {

    @FXML private Label labelInputSize;
    @FXML private Label labelOutputSize;
    @FXML private Label labelInputOutputX;
    @FXML private Label labelInputOutputY;

    @FXML private TextField textFieldInputSizeX;
    @FXML private TextField textFieldInputSizeY;
    @FXML private TextField textFieldOutputSizeX;
    @FXML private TextField textFieldOutputSizeY;

    public ComponentRecurrentParamController(Layer layer) {
        super(layer);
    }

    @Override
    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/design/component/recurrent_param.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        TextFieldUtil.applyPositiveIntegerFilter(textFieldInputSizeX, 1);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldInputSizeY, 1);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldOutputSizeX, 1);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldOutputSizeY, 1);
        setTextFieldByLayerProperties();

        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.title"));
        labelInputOutputX.setText(AppPropertiesSingleton.getInstance().get("frame.component.width"));
        labelInputOutputY.setText(AppPropertiesSingleton.getInstance().get("frame.component.height"));
        labelInputSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.inputSize"));
        labelOutputSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.outputSize"));
    }

    protected void setTextFieldByLayerProperties(){
        detachTextChangedListener(textFieldInputSizeX, textFieldInputSizeY, textFieldOutputSizeY);
        textFieldInputSizeX.setText(String.valueOf(layer.getProperties().getInput().getX()));
        textFieldInputSizeY.setText(String.valueOf(layer.getProperties().getInput().getY()));
        textFieldOutputSizeX.setText(String.valueOf(layer.getProperties().getOutput().getX()));
        textFieldOutputSizeY.setText(String.valueOf(layer.getProperties().getOutput().getY()));
        attachTextChangedListener(textFieldInputSizeX, textFieldInputSizeY, textFieldOutputSizeY);
    }

    @Override
    protected void textFieldChangedHandler(){
        changeInputSize();
        changeOutputSize();
        reshapeBlock();
    }

    private void changeInputSize(){
        int x = TextFieldUtil.parseInteger(textFieldInputSizeX, 1);
        int y = TextFieldUtil.parseInteger(textFieldInputSizeY, 1);
        textFieldOutputSizeX.setText(textFieldInputSizeX.getText());
        layer.getProperties().getInput().setX(x);
        layer.getProperties().getInput().setY(y);
    }

    private void changeOutputSize(){
        int x = TextFieldUtil.parseInteger(textFieldOutputSizeX, 1);
        int y = TextFieldUtil.parseInteger(textFieldOutputSizeY, 1);
        layer.getProperties().getOutput().setX(x);
        layer.getProperties().getOutput().setY(y);
    }
}
