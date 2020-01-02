package org.kok202.dluid.application.content.train;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import org.kok202.dluid.ai.AIFacade;
import org.kok202.dluid.ai.entity.enumerator.Optimizer;
import org.kok202.dluid.ai.entity.enumerator.WeightInitializer;
import org.kok202.dluid.application.adapter.MenuAdapter;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;
import org.kok202.dluid.application.util.TextFieldUtil;


public class ModelTrainParamController extends AbstractModelTrainController {

    @FXML private TitledPane titledPane;
    @FXML private Label labelWeightInit;
    @FXML private Label labelOptimizer;
    @FXML private Label labelLearningRate;
    @FXML private Label labelBatchSize;
    @FXML private Label labelEpoch;

    @FXML private MenuButton menuButtonWeightInit;
    @FXML private MenuButton menuButtonOptimizer;
    @FXML private TextField textFieldLearningRate;
    @FXML private TextField textFieldBatchSize;
    @FXML private TextField textFieldEpoch;

    public AnchorPane createView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/train/parameter.fxml"));
        fxmlLoader.setController(this);
        AnchorPane content = fxmlLoader.load();
        return content;
    }

    @Override
    protected void initialize() throws Exception {
        TextFieldUtil.applyPositiveDoubleFilter(textFieldLearningRate, AIFacade.getTrainLearningRate());
        TextFieldUtil.applyPositiveIntegerFilter(textFieldBatchSize, AIFacade.getTrainBatchSize());
        TextFieldUtil.applyPositiveIntegerFilter(textFieldEpoch, AIFacade.getTrainEpoch());
        textFieldLearningRate.textProperty().addListener(changeListener -> textFieldChangeHandler());
        textFieldBatchSize.textProperty().addListener(changeListener -> textFieldChangeHandler());
        textFieldEpoch.textProperty().addListener(changeListener -> textFieldChangeHandler());
        initializeMenuButtonWeightInit();
        initializeMenuButtonOptimizer();

        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.paramSetting.title"));
        labelWeightInit.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.paramSetting.initializer"));
        labelOptimizer.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.paramSetting.optimizer"));
        labelLearningRate.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.paramSetting.learningRate"));
        labelBatchSize.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.paramSetting.batchSize"));
        labelEpoch.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.paramSetting.epochSize"));
    }

    private void textFieldChangeHandler(){
        setLearningRate();
        setBatchSize();
        setEpoch();
    }

    public void setLearningRate() {
        double value = TextFieldUtil.parseDouble(textFieldLearningRate);
        AIFacade.setTrainLearningRate(value);
    }

    public void setBatchSize() {
        int value = TextFieldUtil.parseInteger(textFieldBatchSize);
        AIFacade.setTrainBatchSize(value);
    }

    public void setEpoch() {
        int value = TextFieldUtil.parseInteger(textFieldEpoch);
        AIFacade.setTrainEpoch(value);
    }

    private void initializeMenuButtonWeightInit(){
        MenuAdapter<WeightInitializer> menuAdapter = new MenuAdapter<>(menuButtonWeightInit);
        menuAdapter.setMenuItemChangedListener(AIFacade::setTrainWeightInit);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.initializer.one"), WeightInitializer.ONES);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.initializer.zero"), WeightInitializer.ZERO);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.initializer.xavier"), WeightInitializer.XAVIER);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.initializer.uniform"), WeightInitializer.UNIFORM);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.initializer.normal"), WeightInitializer.NORMAL);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.initializer.distributionZeroToOne"), WeightInitializer.DISTRIBUTION_ZERO_TO_ONE);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.initializer.distributionPlusMinusOne"), WeightInitializer.DISTRIBUTION_PLUS_MINUS_ONE);
        menuAdapter.setDefaultMenuItem(AIFacade.getTrainWeightInitializer());
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
