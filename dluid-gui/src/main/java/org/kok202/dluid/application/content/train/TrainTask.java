package org.kok202.dluid.application.content.train;

import javafx.concurrent.Task;
import org.kok202.dluid.AppFacade;
import org.kok202.dluid.ai.AIFacade;
import org.kok202.dluid.ai.listener.RunnableTrainingTaskContainer;
import org.kok202.dluid.application.singleton.AppWidgetSingleton;
import org.kok202.dluid.model.ModelStateManager;

public class TrainTask extends Task<Integer> {

    public void bindWithComponent(ModelTrainTaskController modelTrainTaskController) {
        modelTrainTaskController.getProgressBarTrainingProgress().progressProperty().bind(this.progressProperty());
        modelTrainTaskController.getButtonTrainingStop().setOnAction(event -> stopTraining());
        messageProperty().addListener((observable, oldValue, newValue) -> modelTrainTaskController.getTextAreaTrainingLog().appendText(newValue + "\n"));
        AppFacade.setTrainingButtonDisable(false);
    }

    @Override
    protected Integer call() throws Exception {
        validateTrainPossible();
        setTrainListener();
        trainModel();
        updateProgress(100, 100);
        return 100;
    }

    private void validateTrainPossible(){
        updateMessage("Check training possible.");
        ModelStateManager.validateTrainPossible();
        updateMessage("Check training possible. [Successful]");
    }

    private void setTrainListener(){
        updateMessage("Try to add listener for print log.");
        int epochTaskPeriod = 1;
        int batchTaskPeriod = AIFacade.getTrainTotalRecordSize() / AIFacade.getTrainBatchSize() / 10;
        epochTaskPeriod = (epochTaskPeriod <= 0)? 1 : epochTaskPeriod;
        batchTaskPeriod = (batchTaskPeriod <= 0)? 1 : batchTaskPeriod;
        AIFacade.initializeTrainListener(
                epochTaskPeriod,
                batchTaskPeriod,
                (taskContainerObj) -> {
                    // not executed if epoch is not set when
                    RunnableTrainingTaskContainer taskContainer = (RunnableTrainingTaskContainer) taskContainerObj;
                    updateMessage(String.format(
                            "Epoch : %s \n" +
                            "Fitting score : %s",
                            taskContainer.getEpochCounter().getCount(),
                            taskContainer.getScore()));
                },
                (taskContainerObj) -> {
                    RunnableTrainingTaskContainer taskContainer = (RunnableTrainingTaskContainer) taskContainerObj;
                    double currentProgress =
                            taskContainer.getBatchCounter().getCount() +
                                    (taskContainer.getEpochCounter().getCount()-1) * taskContainer.getBatchCounter().getMax();
                    double totalProgress = taskContainer.getEpochCounter().getMax() * taskContainer.getBatchCounter().getMax();

                    int percent = (int) (currentProgress / totalProgress * 100);
                    percent = Math.max(0, Math.min(percent, 100));
                    updateProgress(percent, 100);
                    updateMessage("Batch progress : " + taskContainer.getBatchCounter().calcPercent() + " / 100%");
                });
        updateMessage("Try to add listener for print log. [done]");
    }

    private void trainModel(){
        updateMessage("Training start.");
        AIFacade.trainModel();
    }

    private void stopTraining(){
        cancel();
    }

    @Override
    public void succeeded() {
        updateMessage("Training done.");
        AppFacade.setTrainingButtonDisable(false);
        int learnedEpoch = AIFacade.getModelLearnedEpochNumber();
        int trainedEpoch = AIFacade.getTrainEpoch();
        AIFacade.setModelLearnedEpochNumber(learnedEpoch + trainedEpoch);
        AppWidgetSingleton.getInstance().getTabsController().getTabModelTrainController().refreshModelInfo();
    }
}