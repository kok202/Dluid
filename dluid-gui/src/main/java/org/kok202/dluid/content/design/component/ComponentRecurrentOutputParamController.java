package org.kok202.dluid.content.design.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.kok202.dluid.adapter.MenuAdapter;
import org.kok202.dluid.domain.entity.Layer;
import org.kok202.dluid.domain.entity.enumerator.LossFunctionWrapper;
import org.kok202.dluid.singleton.AppPropertiesSingleton;
import org.kok202.dluid.util.TextFieldUtil;

public class ComponentRecurrentOutputParamController extends AbstractLayerComponentController {

    @FXML private Label labelInputSize;
    @FXML private Label labelOutputSize;
    @FXML private Label labelInputOutputX;
    @FXML private Label labelInputOutputY;
    @FXML private Label labelLossFunction;

    @FXML private TextField textFieldInputSizeX;
    @FXML private TextField textFieldInputSizeY;
    @FXML private TextField textFieldOutputSizeX;
    @FXML private TextField textFieldOutputSizeY;
    @FXML private MenuButton menuButtonLossFunction;

    public ComponentRecurrentOutputParamController(Layer layer) {
        super(layer);
    }

    @Override
    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/design/component/recurrent_output_param.fxml"));
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
        initializeMenuButtonLossFunction();

        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.title"));
        labelInputOutputX.setText(AppPropertiesSingleton.getInstance().get("frame.component.width"));
        labelInputOutputY.setText(AppPropertiesSingleton.getInstance().get("frame.component.height"));
        labelInputSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.inputSize"));
        labelOutputSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.outputSize"));
        labelLossFunction.setText(AppPropertiesSingleton.getInstance().get("frame.component.common.function.lossFunction"));
    }

    protected void setTextFieldByLayerProperties(){
        detachTextChangedListener(textFieldInputSizeX, textFieldInputSizeY, textFieldOutputSizeY);
        textFieldInputSizeX.setText(String.valueOf(layer.getProperties().getInputSizeX()));
        textFieldInputSizeY.setText(String.valueOf(layer.getProperties().getInputSizeY()));
        textFieldOutputSizeX.setText(String.valueOf(layer.getProperties().getOutputSizeX()));
        textFieldOutputSizeY.setText(String.valueOf(layer.getProperties().getOutputSizeY()));
        attachTextChangedListener(textFieldInputSizeX, textFieldInputSizeY, textFieldOutputSizeY);
    }

    private void initializeMenuButtonLossFunction(){
        MenuAdapter<LossFunctionWrapper> menuAdapter = new MenuAdapter<>(menuButtonLossFunction);
        menuAdapter.setMenuItemChangedListener(lossFunction -> {
            layer.getProperties().setLossFunction(lossFunction);
            reshapeBlock();
        });
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.outputFunction.mse"), LossFunctionWrapper.MSE);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.outputFunction.l1"), LossFunctionWrapper.L1);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.outputFunction.l2"), LossFunctionWrapper.L2);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.outputFunction.klDivergence"), LossFunctionWrapper.KL_DIVERGENCE);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.outputFunction.mcxent"), LossFunctionWrapper.MCXENT);

        layer.getProperties().setLossFunction(LossFunctionWrapper.MSE);
        menuAdapter.setDefaultMenuItem(layer.getProperties().getLossFunction());
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
        layer.getProperties().setInputSize(x, y);
    }

    private void changeOutputSize(){
        int x = TextFieldUtil.parseInteger(textFieldOutputSizeX, 1);
        int y = TextFieldUtil.parseInteger(textFieldOutputSizeY, 1);
        layer.getProperties().setOutputSize(x, y);
    }
}
