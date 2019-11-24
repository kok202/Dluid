package org.kok202.deepblock.application.content.model.train;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.deeplearning4j.nn.weights.WeightInit;
import org.kok202.deepblock.AppConstant;
import org.kok202.deepblock.ai.AIFacade;
import org.kok202.deepblock.ai.entity.enumerator.Optimizer;
import org.kok202.deepblock.application.Util.TextFieldUtil;
import org.kok202.deepblock.application.adapter.SplitMenuAdapter;
import org.kok202.deepblock.application.adapter.file.TrainFeatureFileFinder;
import org.kok202.deepblock.application.adapter.file.TrainResultFileFinder;
import org.kok202.deepblock.application.singleton.AppPropertiesSingleton;


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
    @FXML private SplitMenuButton splitMenuWeightInit;
    @FXML private SplitMenuButton splitMenuOptimizer;
    @FXML private TextField textFieldLearningRate;
    @FXML private TextField textFieldBatchSize;
    @FXML private TextField textFieldRecordSize;
    @FXML private TextField textFieldEpoch;

    public AnchorPane createView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/model/train/parameter.fxml"));
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
        initializeSplitMenuWeightInit();
        initializeSplitMenuOptimizer();


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

    private void initializeSplitMenuWeightInit(){
        SplitMenuAdapter<WeightInit> splitMenuAdapter = new SplitMenuAdapter<>(splitMenuWeightInit);
        splitMenuAdapter.setMenuItemChangedListener(AIFacade::setTrainWeightInit);
        splitMenuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.trainTab.dataSetting.initializer.one"), WeightInit.ONES);
        splitMenuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.trainTab.dataSetting.initializer.zero"), WeightInit.ZERO);
        splitMenuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.trainTab.dataSetting.initializer.xavier"), WeightInit.XAVIER);
        splitMenuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.trainTab.dataSetting.initializer.uniform"), WeightInit.UNIFORM);
        splitMenuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.trainTab.dataSetting.initializer.distribution"), WeightInit.DISTRIBUTION);
        splitMenuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.trainTab.dataSetting.initializer.normal"), WeightInit.NORMAL);
        splitMenuAdapter.setDefaultMenuItem(AIFacade.getTrainWeightInit());
    }

    private void initializeSplitMenuOptimizer(){
        SplitMenuAdapter<Optimizer> splitMenuAdapter = new SplitMenuAdapter<>(splitMenuOptimizer);
        splitMenuAdapter.setMenuItemChangedListener(AIFacade::setTrainOptimizer);
        splitMenuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.trainTab.dataSetting.optimizer.sgd"), Optimizer.SGD);
        splitMenuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.trainTab.dataSetting.optimizer.adam"), Optimizer.ADAM);
        splitMenuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.trainTab.dataSetting.optimizer.rmsProp"), Optimizer.RMS_PROP);
        splitMenuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.trainTab.dataSetting.optimizer.noop"), Optimizer.NOOP);
        splitMenuAdapter.setDefaultMenuItem(AIFacade.getTrainOptimizer());
    }
}
