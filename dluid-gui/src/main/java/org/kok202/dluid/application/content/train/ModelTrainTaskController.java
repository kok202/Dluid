package org.kok202.dluid.application.content.train;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lombok.Data;
import org.kok202.dluid.ai.AIFacade;
import org.kok202.dluid.application.adapter.LineChartAdapter;
import org.kok202.dluid.application.common.ExceptionHandler;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;
import org.kok202.dluid.application.util.TextFieldUtil;

@Data
public class ModelTrainTaskController extends AbstractModelTrainController {
    @FXML private TitledPane titledPane;
    @FXML private LineChart<Number, Number> lineChartTrainingChart;

    @FXML private Label labelListeningPeriod;
    @FXML private Label labelBatchSize;
    @FXML private Label labelEpoch;
    @FXML private TextField textFieldBatchSize;
    @FXML private TextField textFieldEpoch;
    @FXML private TextField textFieldListeningPeriod;

    @FXML private TextArea textAreaTrainingLog;
    @FXML private ProgressBar progressBarTrainingProgress;

    @FXML private Button buttonTrainingOneTime;
    @FXML private Button buttonTrainingNTime;
    private LineChartAdapter lineChartAdapter;

    public AnchorPane createView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/train/training_task.fxml"));
        fxmlLoader.setController(this);
        AnchorPane content = fxmlLoader.load();
        return content;
    }

    @Override
    protected void initialize() throws Exception {
        lineChartAdapter = new LineChartAdapter(lineChartTrainingChart);
        buttonTrainingOneTime.setDisable(false);
        buttonTrainingNTime.setDisable(false);
        buttonTrainingOneTime.setOnAction(event -> buttonTrainingOneTimeActionHandler());
        buttonTrainingNTime.setOnAction(event -> buttonTrainingNTimeActionHandler());

        textFieldBatchSize.textProperty().addListener(changeListener -> textFieldChangeHandler());
        textFieldEpoch.textProperty().addListener(changeListener -> textFieldChangeHandler());
        textFieldListeningPeriod.textProperty().addListener(changeListener -> textFieldChangeHandler());

        TextFieldUtil.applyPositiveIntegerFilter(textFieldBatchSize, AIFacade.getTrainBatchSize());
        TextFieldUtil.applyPositiveIntegerFilter(textFieldEpoch, AIFacade.getTrainEpoch());
        TextFieldUtil.applyPositiveIntegerFilter(textFieldListeningPeriod, AIFacade.getListeningPeriod());

        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.trainTask.title"));
        lineChartTrainingChart.setTitle(AppPropertiesSingleton.getInstance().get("frame.trainTab.trainChart.title"));
        labelBatchSize.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.trainTask.batchSize"));
        labelEpoch.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.trainTask.epochSize"));
        labelListeningPeriod.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.trainTask.listeningPeriod"));
        buttonTrainingOneTime.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.trainTask.oneTime"));
        buttonTrainingNTime.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.trainTask.nTime"));
    }

    private void textFieldChangeHandler(){
        setBatchSize();
        setEpoch();
        setEpochListeningPeriod();
    }

    public void setBatchSize() {
        int value = TextFieldUtil.parseInteger(textFieldBatchSize);
        AIFacade.setTrainBatchSize(value);
    }

    public void setEpoch() {
        int value = TextFieldUtil.parseInteger(textFieldEpoch);
        AIFacade.setTrainEpoch(value);
    }

    public void setEpochListeningPeriod() {
        int value = TextFieldUtil.parseInteger(textFieldListeningPeriod);
        AIFacade.setListeningPeriod(value);
    }

    private void buttonTrainingOneTimeActionHandler(){
        AIFacade.setTrainEpoch(1);

        TrainTask trainTask = new TrainTask();
        trainTask.bindWithComponent(this);
        trainTask.exceptionProperty().addListener((observable, oldValue, newValue) -> {
            trainTask.cancel();
            ExceptionHandler.catchException(Thread.currentThread(), newValue);
        });
        Thread thread = new Thread(trainTask);
        thread.setDaemon(true);
        thread.start();
    }

    private void buttonTrainingNTimeActionHandler(){
        setEpoch();

        TrainTask trainTask = new TrainTask();
        trainTask.bindWithComponent(this);
        trainTask.exceptionProperty().addListener((observable, oldValue, newValue) -> {
            trainTask.cancel();
            ExceptionHandler.catchException(Thread.currentThread(), newValue);
        });
        Thread thread = new Thread(trainTask);
        thread.setDaemon(true);
        thread.start();
    }
}
