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
    }

    private void initializeSplitMenuWeightInit(){
        SplitMenuAdapter<WeightInit> splitMenuAdapter = new SplitMenuAdapter<>(splitMenuWeightInit);
        splitMenuAdapter.setMenuItemChangedListener(weightInit -> {
            layer.getProperties().setWeightInit(weightInit);
            notifyLayerDataChanged();
        });
        splitMenuAdapter.addMenuItem("1로 초기화", WeightInit.ONES);
        splitMenuAdapter.addMenuItem("0으로 초기화", WeightInit.ZERO);
        splitMenuAdapter.addMenuItem("Xavier 초기화", WeightInit.XAVIER);
        splitMenuAdapter.addMenuItem("Uniform 초기화", WeightInit.UNIFORM);
        splitMenuAdapter.addMenuItem("Distribution 초기화", WeightInit.DISTRIBUTION);
        splitMenuAdapter.addMenuItem("Normal 초기화", WeightInit.NORMAL);
        splitMenuAdapter.setDefaultMenuItem(layer.getProperties().getWeightInit());
    }

    private void initializeSplitMenuActivationFunction(){
        SplitMenuAdapter<Activation> splitMenuAdapter = new SplitMenuAdapter<>(splitMenuActivationFunction);
        splitMenuAdapter.setMenuItemChangedListener(activation -> {
            layer.getProperties().setActivationFunction(activation);
            notifyLayerDataChanged();
        });
        splitMenuAdapter.addMenuItem("Identity", Activation.IDENTITY);
        splitMenuAdapter.addMenuItem("Sigmoid", Activation.SIGMOID);
        splitMenuAdapter.addMenuItem("ReLU", Activation.RELU);
        splitMenuAdapter.addMenuItem("Leaky ReLU", Activation.LEAKYRELU);
        splitMenuAdapter.addMenuItem("Softmax", Activation.SOFTMAX);
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
