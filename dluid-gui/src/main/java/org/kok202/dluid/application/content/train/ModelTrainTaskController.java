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
import org.kok202.dluid.application.singleton.AppWidgetSingleton;
import org.kok202.dluid.application.util.TextFieldUtil;

@Data
public class ModelTrainTaskController extends AbstractModelTrainController {
    @FXML private TitledPane titledPane;
    @FXML private LineChart<Number, Number> lineChartTrainingChart;
    @FXML private TextArea textAreaTrainingLog;
    @FXML private ProgressBar progressBarTrainingProgress;
    @FXML private Label labelListeningPeriod;
    @FXML private TextField textFieldListeningPeriod;
    @FXML private Button buttonTrainingOneTime;
    @FXML private Button buttonTrainingNTime;
    @FXML private Button buttonTrainingStop;
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
        buttonTrainingStop.setDisable(true);
        buttonTrainingOneTime.setOnAction(event -> buttonTrainingOneTimeActionHandler());
        buttonTrainingNTime.setOnAction(event -> buttonTrainingNTimeActionHandler());
        TextFieldUtil.applyPositiveIntegerFilter(textFieldListeningPeriod, AIFacade.getListeningPeriod());
        textFieldListeningPeriod.textProperty().addListener(changeListener -> textFieldChangeHandler());

        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.trainTask.title"));
        lineChartTrainingChart.setTitle(AppPropertiesSingleton.getInstance().get("frame.trainTab.trainChart.title"));
        labelListeningPeriod.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.trainTask.listeningPeriod"));
        buttonTrainingOneTime.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.trainTask.oneTime"));
        buttonTrainingNTime.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.trainTask.nTime"));
        buttonTrainingStop.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.trainTask.stop"));
    }

    private void textFieldChangeHandler(){
        int value = TextFieldUtil.parseInteger(textFieldListeningPeriod);
        AIFacade.setListeningPeriod(value);
    }

    private void buttonTrainingOneTimeActionHandler(){
        AIFacade.setTrainEpoch(1);

        TrainTask trainTask = new TrainTask();
        trainTask.bindWithComponent(this);
        trainTask.exceptionProperty().addListener((observable, oldValue, newValue) -> ExceptionHandler.catchException(Thread.currentThread(), newValue));
        Thread thread = new Thread(trainTask);
        thread.setDaemon(true);
        thread.start();
    }

    private void buttonTrainingNTimeActionHandler(){
        AppWidgetSingleton.getInstance().getTabsController().getTabModelTrainController().getModelTrainParamController().setEpoch();

        TrainTask trainTask = new TrainTask();
        trainTask.bindWithComponent(this);
        trainTask.exceptionProperty().addListener((observable, oldValue, newValue) -> ExceptionHandler.catchException(Thread.currentThread(), newValue));
        Thread thread = new Thread(trainTask);
        thread.setDaemon(true);
        thread.start();
    }
}
