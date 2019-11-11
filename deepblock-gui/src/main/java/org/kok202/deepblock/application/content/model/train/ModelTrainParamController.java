package org.kok202.deepblock.application.content.model.train;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.deeplearning4j.nn.weights.WeightInit;
import org.kok202.deepblock.ai.entity.enumerator.Optimizer;
import org.kok202.deepblock.ai.interfaces.AIInterface;
import org.kok202.deepblock.application.Util.TextFieldUtil;
import org.kok202.deepblock.application.adapter.SplitMenuAdapter;
import org.kok202.deepblock.application.adapter.file.TrainFeatureFileFinder;
import org.kok202.deepblock.application.adapter.file.TrainResultFileFinder;
import org.kok202.deepblock.application.interfaces.AppConstant;


public class ModelTrainParamController extends AbstractModelTrainController {

    @FXML private Label labelTrainingFeature;
    @FXML private Label labelTrainingResult;
    @FXML private Label labelWeightInit;
    @FXML private Label labelOptimizer;
    @FXML private Label labelLearningRate;
    @FXML private Label labelBatchSize;
    @FXML private Label labelRecordize;
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
    }

    private void textFieldChangeHandler(){
        setLearningRate();
        setBatchSize();
        setRecordSize();
        setEpoch();
    }

    private void setLearningRate() {
        double value = TextFieldUtil.parseDouble(textFieldLearningRate);
        AIInterface.setTrainLearningRate(value);
    }

    private void setBatchSize() {
        int value = TextFieldUtil.parseInteger(textFieldBatchSize);
        AIInterface.setTrainBatchSize(value);
    }

    private void setRecordSize() {
        int value = TextFieldUtil.parseInteger(textFieldRecordSize);
        AIInterface.setTrainTotalRecordSize(value);
    }

    private void setEpoch() {
        int value = TextFieldUtil.parseInteger(textFieldEpoch);
        AIInterface.setTrainEpoch(value);
    }

    private void setButtonFeatureFinderActionHandler(){
        TrainFeatureFileFinder trainFeatureFileFinder = new TrainFeatureFileFinder(textFieldFindTrainingFeature, buttonFindTrainingFeature);
        trainFeatureFileFinder.initialize();
        trainFeatureFileFinder.setCallbackAfterLoad(() -> textFieldRecordSize.setText(AIInterface.getTrainTotalRecordSize() + ""));
    }

    private void setButtonResultFinderActionHandler(){
        TrainResultFileFinder trainResultFileFinder = new TrainResultFileFinder(textFieldFindTrainingResult, buttonFindTrainingResult);
        trainResultFileFinder.initialize();
        trainResultFileFinder.setCallbackAfterLoad(() -> textFieldRecordSize.setText(AIInterface.getTrainTotalRecordSize() + ""));
        //FIXME : feature 랑 result 랑 사이즈가 맞는지도 비교
    }

    private void initializeSplitMenuWeightInit(){
        SplitMenuAdapter<WeightInit> splitMenuAdapter = new SplitMenuAdapter<>(splitMenuWeightInit);
        splitMenuAdapter.setMenuItemChangedListener(AIInterface::setTrainWeightInit);
        splitMenuAdapter.addMenuItem("1로 초기화", WeightInit.ONES);
        splitMenuAdapter.addMenuItem("0으로 초기화", WeightInit.ZERO);
        splitMenuAdapter.addMenuItem("Xavier 초기화", WeightInit.XAVIER);
        splitMenuAdapter.addMenuItem("Uniform 초기화", WeightInit.UNIFORM);
        splitMenuAdapter.addMenuItem("Distribution 초기화", WeightInit.DISTRIBUTION);
        splitMenuAdapter.addMenuItem("Normal 초기화", WeightInit.NORMAL);
        splitMenuAdapter.setDefaultMenuItem(AIInterface.getTrainWeightInit());
    }

    private void initializeSplitMenuOptimizer(){
        SplitMenuAdapter<Optimizer> splitMenuAdapter = new SplitMenuAdapter<>(splitMenuOptimizer);
        splitMenuAdapter.setMenuItemChangedListener(AIInterface::setTrainOptimizer);
        splitMenuAdapter.addMenuItem("Gradient descent optimizer", Optimizer.SGD);
        splitMenuAdapter.addMenuItem("ADAM optimizer", Optimizer.ADAM);
        splitMenuAdapter.addMenuItem("No optimizer", Optimizer.NOOP);
        splitMenuAdapter.setDefaultMenuItem(AIInterface.getTrainOptimizer());
    }
}
