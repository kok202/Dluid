package org.kok202.dluid.application.content.train;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import lombok.Data;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;

@Data
public class ModelTrainTaskController extends AbstractModelTrainController {
    @FXML private TitledPane titledPane;
    @FXML private LineChart lineChartTrainingChart;
    @FXML private TextArea textAreaTrainingLog;
    @FXML private ProgressBar progressBarTrainingProgress;
    @FXML private Button buttonTrainingOneTime;
    @FXML private Button buttonTrainingNTime;
    @FXML private Button buttonTrainingStop;

    public AnchorPane createView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/train/training_task.fxml"));
        fxmlLoader.setController(this);
        AnchorPane content = fxmlLoader.load();
        return content;
    }

    @Override
    protected void initialize() throws Exception {
        buttonTrainingOneTime.setDisable(false);
        buttonTrainingNTime.setDisable(false);
        buttonTrainingStop.setDisable(true);
        buttonTrainingOneTime.setOnAction(event -> buttonTrainingOneTimeActionHandler());
        buttonTrainingNTime.setOnAction(event -> buttonTrainingNTimeActionHandler());
        titledPane.setExpanded(false);

        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.trainTask.title"));
        buttonTrainingOneTime.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.trainTask.oneTime"));
        buttonTrainingNTime.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.trainTask.nTime"));
        buttonTrainingStop.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.trainTask.stop"));
    }

    private void buttonTrainingOneTimeActionHandler(){
        TrainTask trainTask = new TrainTask();
        trainTask.bindWithComponent(this);
        Thread thread = new Thread(trainTask);
        thread.setDaemon(true);
        thread.start();
    }

    private void buttonTrainingNTimeActionHandler(){
        TrainTask trainTask = new TrainTask();
        trainTask.bindWithComponent(this);
        Thread thread = new Thread(trainTask);
        thread.setDaemon(true);
        thread.start();
    }
}
