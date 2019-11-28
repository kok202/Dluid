package org.kok202.dluid.application.content.design.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.application.Util.TextFieldUtil;
import org.kok202.dluid.application.adapter.SplitMenuAdapter;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class ComponentOutputParamController extends AbstractLayerComponentController {

    @FXML private Label labelInputSize;
    @FXML private Label labelOutputSize;
    @FXML private Label labelLossFunction;

    @FXML private TextField textFieldInputSize;
    @FXML private TextField textFieldOutputSize;
    @FXML private SplitMenuButton splitMenuLossFunction;

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
        TextFieldUtil.applyPositiveIntegerFilter(textFieldInputSize, 1);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldOutputSize, 1);
        setTextFieldByLayerProperties();
        initializeSplitMenuLossFunction();

        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.title"));
        labelInputSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.inputSize"));
        labelOutputSize.setText(AppPropertiesSingleton.getInstance().get("frame.component.default.outputSize"));
        labelLossFunction.setText(AppPropertiesSingleton.getInstance().get("frame.component.common.function.lossFunction"));
    }

    private void setTextFieldByLayerProperties(){
        textFieldInputSize.setText(String.valueOf(layer.getProperties().getInputSize()[0]));
        textFieldOutputSize.setText(String.valueOf(layer.getProperties().getOutputSize()[0]));
        attachTextChangedListener(textFieldInputSize, textFieldOutputSize);
    }

    private void initializeSplitMenuLossFunction(){
        SplitMenuAdapter<LossFunctions.LossFunction> splitMenuAdapter = new SplitMenuAdapter<>(splitMenuLossFunction);
        splitMenuAdapter.setMenuItemChangedListener(lossFunction -> {
            layer.getProperties().setLossFunction(lossFunction);
            notifyLayerDataChanged();
        });
        splitMenuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.outputFunction.mse"), LossFunctions.LossFunction.MSE);
        splitMenuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.outputFunction.l1"), LossFunctions.LossFunction.L1);
        splitMenuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.outputFunction.l2"), LossFunctions.LossFunction.L2);
        splitMenuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.outputFunction.klDivergence"), LossFunctions.LossFunction.KL_DIVERGENCE);

        layer.getProperties().setLossFunction(LossFunctions.LossFunction.MSE);
        splitMenuAdapter.setDefaultMenuItem(layer.getProperties().getLossFunction());
    }

    @Override
    protected void textFieldChangedHandler(){
        changeInputSize();
        changeOutputSize();
        notifyLayerDataChanged();
    }

    private void changeInputSize(){
        int x = TextFieldUtil.parseInteger(textFieldInputSize, 1);
        layer.getProperties().setInputSize(x);
    }

    private void changeOutputSize(){
        int y = TextFieldUtil.parseInteger(textFieldOutputSize, 1);
        layer.getProperties().setOutputSize(y);
    }
}
