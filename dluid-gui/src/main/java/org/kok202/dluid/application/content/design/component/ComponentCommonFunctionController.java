package org.kok202.dluid.application.content.design.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.entity.enumerator.ActivationWrapper;
import org.kok202.dluid.ai.entity.enumerator.WeightInitilaizer;
import org.kok202.dluid.application.adapter.MenuAdapter;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;

public class ComponentCommonFunctionController extends AbstractLayerComponentController {

    @FXML private Label labelWeightInitializer;
    @FXML private Label labelActivationFunction;
    @FXML private Label labelDropout;

    @FXML private MenuButton menuButtonWeightInit;
    @FXML private MenuButton menuButtonActivationFunction;
    @FXML private Slider sliderDropout;

    public ComponentCommonFunctionController(Layer layer) {
        super(layer);
    }

    @Override
    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/design/component/common_function.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        initializeMenuButtonWeightInit();
        initializeMenuButtonActivationFunction();
        initializeSliderDropout();
        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.component.common.function.title"));
        labelWeightInitializer.setText(AppPropertiesSingleton.getInstance().get("frame.component.common.function.Initializer"));
        labelActivationFunction.setText(AppPropertiesSingleton.getInstance().get("frame.component.common.function.activationFunction"));
        labelDropout.setText(AppPropertiesSingleton.getInstance().get("frame.component.common.function.dropOut"));
    }

    private void initializeMenuButtonWeightInit(){
        MenuAdapter<WeightInitilaizer> menuAdapter = new MenuAdapter<>(menuButtonWeightInit);
        menuAdapter.setMenuItemChangedListener(weightInit -> {
            layer.getProperties().setWeightInit(weightInit);
            notifyLayerDataChanged();
        });
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.initializer.one"), WeightInitilaizer.ONES);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.initializer.zero"), WeightInitilaizer.ZERO);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.initializer.xavier"), WeightInitilaizer.XAVIER);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.initializer.uniform"), WeightInitilaizer.UNIFORM);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.initializer.normal"), WeightInitilaizer.NORMAL);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.initializer.distributionZeroToOne"), WeightInitilaizer.DISTRIBUTION_ZERO_TO_ONE);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.initializer.distributionPlusMinusOne"), WeightInitilaizer.DISTRIBUTION_PLUS_MINUS_ONE);
        menuAdapter.setDefaultMenuItem(layer.getProperties().getWeightInit());
    }

    private void initializeMenuButtonActivationFunction(){
        MenuAdapter<ActivationWrapper> menuAdapter = new MenuAdapter<>(menuButtonActivationFunction);
        menuAdapter.setMenuItemChangedListener(activation -> {
            layer.getProperties().setActivationFunction(activation);
            notifyLayerDataChanged();
        });
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.activationFunction.identity"), ActivationWrapper.IDENTITY);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.activationFunction.sigmoid"), ActivationWrapper.SIGMOID);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.activationFunction.tanh"), ActivationWrapper.TANH);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.activationFunction.reLU"), ActivationWrapper.RELU);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.activationFunction.leakyReLU"), ActivationWrapper.LEAKYRELU);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.activationFunction.softMax"), ActivationWrapper.SOFTMAX);
        menuAdapter.setDefaultMenuItem(layer.getProperties().getActivationFunction());
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
