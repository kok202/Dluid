package org.kok202.dluid.application.content.train;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.kok202.dluid.AppConstant;
import org.kok202.dluid.ai.AIFacade;
import org.kok202.dluid.ai.entity.enumerator.Optimizer;
import org.kok202.dluid.ai.entity.enumerator.WeightInitWrapper;
import org.kok202.dluid.application.Util.TextFieldUtil;
import org.kok202.dluid.application.adapter.MenuAdapter;
import org.kok202.dluid.application.adapter.file.TrainFeatureFileFinder;
import org.kok202.dluid.application.adapter.file.TrainResultFileFinder;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;
import org.kok202.dluid.domain.exception.InvalidBatchSize;


public class ModelTrainParamController extends AbstractModelTrainController {

    @FXML private TitledPane titledPane;
    @FXML private Label labelTrainingFeature;
    @FXML private Label labelTrainingResult;
    @FXML private Label labelWeightInit;
    @FXML private Label labelOptimizer;
    @FXML private Label labelLearningRate;
    @FXML private Label labelBatchSize;
    @FXML private Label labelRecordSize;
    @FXML private Label labelEpoch;

    @FXML private TextField textFieldFindTrainingFeature;
    @FXML private Button buttonFindTrainingFeature;
    @FXML private TextField textFieldFindTrainingResult;
    @FXML private Button buttonFindTrainingResult;
    @FXML private MenuButton menuButtonWeightInit;
    @FXML private MenuButton menuButtonOptimizer;
    @FXML private TextField textFieldLearningRate;
    @FXML private TextField textFieldBatchSize;
    @FXML private TextField textFieldRecordSize;
    @FXML private TextField textFieldEpoch;

    public AnchorPane createView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/train/parameter.fxml"));
        fxmlLoader.setController(this);
        AnchorPane content = fxmlLoader.load();
        return content;
    }

    @Override
    protected void initialize() throws Exception {
        TextFieldUtil.applyPositiveDoubleFilter(textFieldLearningRate, AppConstant.DEFAULT_LEARNING_RATE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldBatchSize, AppConstant.DEFAULT_BATCH_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldRecordSize, AppConstant.DEFAULT_RECORD_SIZE);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldEpoch, AppConstant.DEFAULT_EPOCH_SIZE);
        textFieldLearningRate.textProperty().addListener(changeListener -> textFieldChangeHandler());
        textFieldBatchSize.textProperty().addListener(changeListener -> textFieldChangeHandler());
        textFieldRecordSize.textProperty().addListener(changeListener -> textFieldChangeHandler());
        textFieldEpoch.textProperty().addListener(changeListener -> textFieldChangeHandler());
        setButtonFeatureFinderActionHandler();
        setButtonResultFinderActionHandler();
        initializeMenuButtonWeightInit();
        initializeMenuButtonOptimizer();


        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.dataSetting.title"));
        labelTrainingFeature.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.dataSetting.trainFeatureData"));
        labelTrainingResult.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.dataSetting.trainResultData"));
        labelWeightInit.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.dataSetting.initializer"));
        labelOptimizer.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.dataSetting.optimizer"));
        labelLearningRate.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.dataSetting.learningRate"));
        labelBatchSize.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.dataSetting.batchSize"));
        labelRecordSize.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.dataSetting.totalSize"));
        labelEpoch.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.dataSetting.epochSize"));
    }

    private void textFieldChangeHandler(){
        setLearningRate();
        setBatchSize();
        setRecordSize();
        setEpoch();
    }

    private void setLearningRate() {
        double value = TextFieldUtil.parseDouble(textFieldLearningRate);
        AIFacade.setTrainLearningRate(value);
    }

    private void setBatchSize() {
        int value = TextFieldUtil.parseInteger(textFieldBatchSize);
        if(value > AIFacade.getTrainTotalRecordSize())
            throw new InvalidBatchSize(AIFacade.getTrainTotalRecordSize());
        AIFacade.setTrainBatchSize(value);
    }

    private void setRecordSize() {
        int value = TextFieldUtil.parseInteger(textFieldRecordSize);
        AIFacade.setTrainTotalRecordSize(value);
    }

    private void setEpoch() {
        int value = TextFieldUtil.parseInteger(textFieldEpoch);
        AIFacade.setTrainEpoch(value);
    }

    private void setButtonFeatureFinderActionHandler(){
        TrainFeatureFileFinder trainFeatureFileFinder = new TrainFeatureFileFinder(textFieldFindTrainingFeature, buttonFindTrainingFeature);
        trainFeatureFileFinder.initialize();
        trainFeatureFileFinder.setCallbackAfterLoad(() -> textFieldRecordSize.setText(AIFacade.getTrainTotalRecordSize() + ""));
    }

    private void setButtonResultFinderActionHandler(){
        TrainResultFileFinder trainResultFileFinder = new TrainResultFileFinder(textFieldFindTrainingResult, buttonFindTrainingResult);
        trainResultFileFinder.initialize();
        trainResultFileFinder.setCallbackAfterLoad(() -> textFieldRecordSize.setText(AIFacade.getTrainTotalRecordSize() + ""));
        //FIXME : feature 랑 result 랑 사이즈가 맞는지도 비교
    }

    private void initializeMenuButtonWeightInit(){
        MenuAdapter<WeightInitWrapper> menuAdapter = new MenuAdapter<>(menuButtonWeightInit);
        menuAdapter.setMenuItemChangedListener(AIFacade::setTrainWeightInit);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.initializer.one"), WeightInitWrapper.ONES);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.initializer.zero"), WeightInitWrapper.ZERO);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.initializer.xavier"), WeightInitWrapper.XAVIER);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.initializer.uniform"), WeightInitWrapper.UNIFORM);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.initializer.normal"), WeightInitWrapper.NORMAL);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.initializer.distributionZeroToOne"), WeightInitWrapper.DISTRIBUTION_ZERO_TO_ONE);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.initializer.distributionPlusMinusOne"), WeightInitWrapper.DISTRIBUTION_PLUS_MINUS_ONE);
        menuAdapter.setDefaultMenuItem(AIFacade.getTrainWeightInit());
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
