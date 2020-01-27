package org.kok202.dluid.application.content.train;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.kok202.dluid.ai.AIFacade;
import org.kok202.dluid.ai.entity.enumerator.BiasInitializer;
import org.kok202.dluid.ai.entity.enumerator.Optimizer;
import org.kok202.dluid.ai.entity.enumerator.WeightInitializer;
import org.kok202.dluid.application.adapter.MenuAdapter;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;
import org.kok202.dluid.application.util.TextFieldUtil;
import org.kok202.dluid.model.ModelStateManager;


public class ModelInformationController extends AbstractModelTrainController {
    @FXML private TitledPane titledPane;
    @FXML private Label labelModelName;
    @FXML private Label labelWeightInit;
    @FXML private Label labelOptimizer;
    @FXML private Label labelLearningRate;
    @FXML private Label labelEpochNumber;

    @FXML private TextField textFieldModelName;
    @FXML private MenuButton menuButtonWeightInit;
    @FXML private MenuButton menuButtonBiasInit;
    @FXML private MenuButton menuButtonOptimizer;
    @FXML private TextField textFieldLearningRate;
    @FXML private TextField textFieldEpochNumber;
    @FXML private Button buttonInitialize;

    public AnchorPane createView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/train/info.fxml"));
        fxmlLoader.setController(this);
        AnchorPane content = fxmlLoader.load();
        return content;
    }

    @Override
    protected void initialize() throws Exception {
        TextFieldUtil.applyPositiveDoubleFilter(textFieldLearningRate, AIFacade.getTrainLearningRate());
        textFieldModelName.textProperty().addListener(changeListener -> textFieldChangeHandler());
        textFieldLearningRate.textProperty().addListener(changeListener -> textFieldChangeHandler());
        buttonInitialize.setOnAction(event -> ModelStateManager.initializeModel());

        initializeMenuButtonWeightInit();
        initializeMenuButtonBiasInit();
        initializeMenuButtonOptimizer();

        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.modelInfo.title"));
        labelModelName.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.modelInfo.name"));
        labelWeightInit.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.modelInfo.initializer"));
        labelOptimizer.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.modelInfo.optimizer"));
        labelLearningRate.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.modelInfo.learningRate"));
        labelEpochNumber.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.modelInfo.totalEpoch"));
        buttonInitialize.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.modelInfo.initialize"));
    }

    private void textFieldChangeHandler(){
        setLearningRate();
        setModeNameChanged();
    }

    public void setLearningRate() {
        double value = TextFieldUtil.parseDouble(textFieldLearningRate);
        AIFacade.setTrainLearningRate(value);
    }

    private void setModeNameChanged() {
        AIFacade.setModelName(textFieldModelName.getText());
    }

    public void refreshModelInformation(){
        textFieldModelName.setText(AIFacade.getModelName());
        textFieldEpochNumber.setText(AIFacade.getModelLearnedEpochNumber() + "");
    }

    private void initializeMenuButtonWeightInit(){
        MenuAdapter<WeightInitializer> menuAdapter = new MenuAdapter<>(menuButtonWeightInit);
        menuAdapter.setMenuItemChangedListener(AIFacade::setTrainWeightInitializer);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.weightInitializer.one"), WeightInitializer.ONES);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.weightInitializer.zero"), WeightInitializer.ZERO);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.weightInitializer.xavier"), WeightInitializer.XAVIER);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.weightInitializer.uniform"), WeightInitializer.UNIFORM);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.weightInitializer.normal"), WeightInitializer.NORMAL);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.weightInitializer.distributionZeroToOne"), WeightInitializer.DISTRIBUTION_ZERO_TO_ONE);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.weightInitializer.distributionPlusMinusOne"), WeightInitializer.DISTRIBUTION_PLUS_MINUS_ONE);
        menuAdapter.setDefaultMenuItem(AIFacade.getTrainWeightInitializer());
    }

    private void initializeMenuButtonBiasInit(){
        MenuAdapter<BiasInitializer> menuAdapter = new MenuAdapter<>(menuButtonBiasInit);
        menuAdapter.setMenuItemChangedListener(AIFacade::setTrainBiasInitializer);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.biasInitializer.zero"), BiasInitializer.ZERO);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.biasInitializer.smallValue"), BiasInitializer.SMALL_VALUE);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.biasInitializer.one"), BiasInitializer.ONES);
        menuAdapter.setDefaultMenuItem(AIFacade.getTrainBiasInitializer());
    }

    private void initializeMenuButtonOptimizer(){
        MenuAdapter<Optimizer> menuAdapter = new MenuAdapter<>(menuButtonOptimizer);
        menuAdapter.setMenuItemChangedListener(AIFacade::setTrainOptimizer);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.optimizer.sgd"), Optimizer.SGD);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.optimizer.adam"), Optimizer.ADAM);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.optimizer.rmsProp"), Optimizer.RMS_PROP);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.optimizer.noop"), Optimizer.NOOP);
        menuAdapter.setDefaultMenuItem(AIFacade.getTrainOptimizer());
    }
}
