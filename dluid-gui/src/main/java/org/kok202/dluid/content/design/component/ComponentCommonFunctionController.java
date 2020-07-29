package org.kok202.dluid.content.design.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import org.kok202.dluid.adapter.MenuAdapter;
import org.kok202.dluid.domain.entity.Layer;
import org.kok202.dluid.domain.entity.enumerator.ActivationWrapper;
import org.kok202.dluid.domain.entity.enumerator.BiasInitializer;
import org.kok202.dluid.domain.entity.enumerator.WeightInitializer;
import org.kok202.dluid.singleton.AppPropertiesSingleton;

@Getter
public class ComponentCommonFunctionController extends AbstractLayerComponentController {

    @FXML private Label labelWeightInitializer;
    @FXML private Label labelBiasInitializer;
    @FXML private Label labelActivationFunction;
    @FXML private Label labelDropout;

    @FXML private MenuButton menuButtonWeightInit;
    @FXML private MenuButton menuButtonBiasInit;
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
        initializeMenuButtonBiasInit();
        initializeMenuButtonActivationFunction();
        initializeSliderDropout();
        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.component.common.function.title"));
        labelWeightInitializer.setText(AppPropertiesSingleton.getInstance().get("frame.component.common.function.weightInitializer"));
        labelBiasInitializer.setText(AppPropertiesSingleton.getInstance().get("frame.component.common.function.biasInitializer"));
        labelActivationFunction.setText(AppPropertiesSingleton.getInstance().get("frame.component.common.function.activationFunction"));
        labelDropout.setText(AppPropertiesSingleton.getInstance().get("frame.component.common.function.dropOut"));
    }

    protected void initializeMenuButtonWeightInit(){
        MenuAdapter<WeightInitializer> menuAdapter = new MenuAdapter<>(menuButtonWeightInit);
        menuAdapter.setMenuItemChangedListener(weightInit -> {
            layer.getProperties().setWeightInitializer(weightInit);
            reshapeBlock();
        });
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.weightInitializer.followGlobal"), WeightInitializer.FOLLOW_GLOBAL_SETTING);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.weightInitializer.one"), WeightInitializer.ONES);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.weightInitializer.zero"), WeightInitializer.ZERO);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.weightInitializer.xavier"), WeightInitializer.XAVIER);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.weightInitializer.uniform"), WeightInitializer.UNIFORM);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.weightInitializer.normal"), WeightInitializer.NORMAL);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.weightInitializer.distributionZeroToOne"), WeightInitializer.DISTRIBUTION_ZERO_TO_ONE);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.weightInitializer.distributionPlusMinusOne"), WeightInitializer.DISTRIBUTION_PLUS_MINUS_ONE);
        menuAdapter.setDefaultMenuItem(layer.getProperties().getWeightInitializer());
    }

    protected void initializeMenuButtonBiasInit(){
        MenuAdapter<BiasInitializer> menuAdapter = new MenuAdapter<>(menuButtonBiasInit);
        menuAdapter.setMenuItemChangedListener(biasInitializer -> {
            layer.getProperties().setBiasInitializer(biasInitializer);
            reshapeBlock();
        });
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.biasInitializer.followGlobal"), BiasInitializer.FOLLOW_GLOBAL_SETTING);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.biasInitializer.smallValue"), BiasInitializer.SMALL_VALUE);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.biasInitializer.zero"), BiasInitializer.ZERO);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.biasInitializer.one"), BiasInitializer.ONES);
        menuAdapter.setDefaultMenuItem(layer.getProperties().getBiasInitializer());
    }

    protected void initializeMenuButtonActivationFunction(){
        MenuAdapter<ActivationWrapper> menuAdapter = new MenuAdapter<>(menuButtonActivationFunction);
        menuAdapter.setMenuItemChangedListener(activation -> {
            layer.getProperties().setActivationFunction(activation);
            reshapeBlock();
        });
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.activationFunction.identity"), ActivationWrapper.IDENTITY);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.activationFunction.sigmoid"), ActivationWrapper.SIGMOID);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.activationFunction.tanh"), ActivationWrapper.TANH);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.activationFunction.reLU"), ActivationWrapper.RELU);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.activationFunction.leakyReLU"), ActivationWrapper.LEAKYRELU);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.activationFunction.softMax"), ActivationWrapper.SOFTMAX);
        menuAdapter.setDefaultMenuItem(layer.getProperties().getActivationFunction());
    }

    protected void initializeSliderDropout(){
        sliderDropout.setValue(layer.getProperties().getDropout());
        sliderDropout.valueProperty().addListener((observable, oldValue, newValue) -> {
            double inputRetainProbability = newValue.doubleValue();
            layer.getProperties().setDropout(inputRetainProbability);
            reshapeBlock();
        });
    }
}
