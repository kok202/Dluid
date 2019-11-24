package org.kok202.deepblock.application.content.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.layout.AnchorPane;
import org.deeplearning4j.nn.weights.WeightInit;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.application.adapter.SplitMenuAdapter;
import org.kok202.deepblock.application.singleton.AppPropertiesSingleton;
import org.nd4j.linalg.activations.Activation;

public class ComponentCommonFunctionController extends AbstractLayerComponentController {

    @FXML private Label labelWeightInitializer;
    @FXML private Label labelActivationFunction;
    @FXML private Label labelDropout;

    @FXML private SplitMenuButton splitMenuWeightInit;
    @FXML private SplitMenuButton splitMenuActivationFunction;
    @FXML private Slider sliderDropout;

    public ComponentCommonFunctionController(Layer layer) {
        super(layer);
    }

    @Override
    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/component/common_function.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        initializeSplitMenuWeightInit();
        initializeSplitMenuActivationFunction();
        initializeSliderDropout();
        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.component.common.function.title"));
        labelWeightInitializer.setText(AppPropertiesSingleton.getInstance().get("frame.component.common.function.Initializer"));
        labelActivationFunction.setText(AppPropertiesSingleton.getInstance().get("frame.component.common.function.activationFunction"));
        labelDropout.setText(AppPropertiesSingleton.getInstance().get("frame.component.common.function.dropOut"));
    }

    private void initializeSplitMenuWeightInit(){
        SplitMenuAdapter<WeightInit> splitMenuAdapter = new SplitMenuAdapter<>(splitMenuWeightInit);
        splitMenuAdapter.setMenuItemChangedListener(weightInit -> {
            layer.getProperties().setWeightInit(weightInit);
            notifyLayerDataChanged();
        });
        splitMenuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.initializer.one"), WeightInit.ONES);
        splitMenuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.initializer.zero"), WeightInit.ZERO);
        splitMenuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.initializer.xavier"), WeightInit.XAVIER);
        splitMenuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.initializer.uniform"), WeightInit.UNIFORM);
        splitMenuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.initializer.distribution"), WeightInit.DISTRIBUTION);
        splitMenuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.initializer.normal"), WeightInit.NORMAL);
        splitMenuAdapter.setDefaultMenuItem(layer.getProperties().getWeightInit());
    }

    private void initializeSplitMenuActivationFunction(){
        SplitMenuAdapter<Activation> splitMenuAdapter = new SplitMenuAdapter<>(splitMenuActivationFunction);
        splitMenuAdapter.setMenuItemChangedListener(activation -> {
            layer.getProperties().setActivationFunction(activation);
            notifyLayerDataChanged();
        });
        splitMenuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.activationFunction.identity"), Activation.IDENTITY);
        splitMenuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.activationFunction.sigmoid"), Activation.SIGMOID);
        splitMenuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.activationFunction.relu"), Activation.RELU);
        splitMenuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.activationFunction.leakyRelu"), Activation.LEAKYRELU);
        splitMenuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.activationFunction.softmax"), Activation.SOFTMAX);
        splitMenuAdapter.setDefaultMenuItem(layer.getProperties().getActivationFunction());
    }

    private void initializeSliderDropout(){
        sliderDropout.setValue(layer.getProperties().getDropout());
        sliderDropout.valueProperty().addListener((observable, oldValue, newValue) -> {
            double inputRetainProbability = newValue.doubleValue();
            layer.getProperties().setDropout(inputRetainProbability);
            notifyLayerDataChanged();
        });
    }
}
