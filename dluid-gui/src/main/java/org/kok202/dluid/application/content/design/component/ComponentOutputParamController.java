package org.kok202.dluid.application.content.design.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.application.adapter.MenuAdapter;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;
import org.kok202.dluid.application.util.TextFieldUtil;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class ComponentOutputParamController extends AbstractLayerComponentController {

    @FXML private Label labelWidth;
    @FXML private Label labelHeight;
    @FXML private Label labelInputSize;
    @FXML private Label labelOutputSize;
    @FXML private Label labelLossFunction;

    @FXML private TextField textFieldInputSizeX;
    @FXML private TextField textFieldInputSizeY;
    @FXML private TextField textFieldOutputSize;
    @FXML private MenuButton menuButtonLossFunction;

    public ComponentOutputParamController(Layer layer) {
        super(layer);
    }

    @Override
    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/design/component/output_param.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        TextFieldUtil.applyPositiveIntegerFilter(textFieldInputSizeX, 1);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldInputSizeY, 1);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldOutputSize, 1);
        setTextFieldByLayerProperties();
        initializeMenuButtonLossFunction();

        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.title"));
        labelWidth.setText(AppPropertiesSingleton.getInstance().get("frame.component.2d.width"));
        labelHeight.setText(AppPropertiesSingleton.getInstance().get("frame.component.2d.height"));
        labelInputSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.inputSize"));
        labelOutputSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.outputSize"));
        labelLossFunction.setText(AppPropertiesSingleton.getInstance().get("frame.component.common.function.lossFunction"));
    }

    private void setTextFieldByLayerProperties(){
        textFieldInputSizeX.setText(String.valueOf(layer.getProperties().getInputSize()[0]));
        textFieldInputSizeY.setText(String.valueOf(layer.getProperties().getInputSize()[1]));
        textFieldOutputSize.setText(String.valueOf(layer.getProperties().getOutputSize()[0]));
        attachTextChangedListener(textFieldInputSizeX, textFieldInputSizeY, textFieldOutputSize);
    }

    private void initializeMenuButtonLossFunction(){
        MenuAdapter<LossFunctions.LossFunction> menuAdapter = new MenuAdapter<>(menuButtonLossFunction);
        menuAdapter.setMenuItemChangedListener(lossFunction -> {
            layer.getProperties().setLossFunction(lossFunction);
            notifyLayerDataChanged();
        });
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.outputFunction.mse"), LossFunctions.LossFunction.MSE);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.outputFunction.l1"), LossFunctions.LossFunction.L1);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.outputFunction.l2"), LossFunctions.LossFunction.L2);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.outputFunction.klDivergence"), LossFunctions.LossFunction.KL_DIVERGENCE);

        layer.getProperties().setLossFunction(LossFunctions.LossFunction.MSE);
        menuAdapter.setDefaultMenuItem(layer.getProperties().getLossFunction());
    }

    @Override
    protected void textFieldChangedHandler(){
        changeInputSize();
        changeOutputSize();
        notifyLayerDataChanged();
    }

    private void changeInputSize(){
        int x = TextFieldUtil.parseInteger(textFieldInputSizeX, 1);
        int y = TextFieldUtil.parseInteger(textFieldInputSizeY, 1);
        layer.getProperties().setInputSize(x, y);
    }

    private void changeOutputSize(){
        int y = TextFieldUtil.parseInteger(textFieldOutputSize, 1);
        layer.getProperties().setOutputSize(y);
    }
}
