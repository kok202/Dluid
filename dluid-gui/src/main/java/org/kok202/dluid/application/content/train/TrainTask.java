package org.kok202.dluid.application.content.train;

import javafx.concurrent.Task;
import org.kok202.dluid.AppFacade;
import org.kok202.dluid.ai.AIFacade;
import org.kok202.dluid.ai.listener.RunnableTrainingTaskContainer;
import org.kok202.dluid.application.singleton.AppWidgetSingleton;

public class TrainTask extends Task<Integer> {

    public void bindWithComponent(ModelTrainTaskController modelTrainTaskController) {
        modelTrainTaskController.getProgressBarTrainingProgress().progressProperty().bind(this.progressProperty());
        modelTrainTaskController.getButtonTrainingStop().setOnAction(event -> stopTraining());
        AppFacade.setTrainingButtonDisable(false);
    }

    @Override
    protected Integer call() throws Exception {
        AppFacade.appendTextOnTrainingLog("Try to add listener for print log.");
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
                    AppFacade.appendTextOnTrainingLog("Epoch : " + taskContainer.getEpochCounter().getCount());
                    AppFacade.appendTextOnTrainingLog("Fitting score : " + taskContainer.getScore());
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
                    updateMessage(String.valueOf(taskContainer.getBatchCounter().calcPercent()));
                    AppFacade.appendTextOnTrainingLog("Batch progress : " + taskContainer.getBatchCounter().calcPercent() + " / 100%");
                });
        AppFacade.appendTextOnTrainingLog("Try to add listener for print log. [done]");
        AppFacade.appendTextOnTrainingLog("Training start.");
        AIFacade.trainModel();
        updateProgress(100, 100);
        updateMessage(String.valueOf(100));
        return 100;
    }

    @Override
    public void succeeded() {
        AppFacade.appendTextOnTrainingLog("Training done.");
        AppFacade.setTrainingButtonDisable(false);
        int learnedEpoch = AIFacade.getModelLearnedEpochNumber();
        int trainedEpoch = AIFacade.getTrainEpoch();
        AIFacade.setModelLearnedEpochNumber(learnedEpoch + trainedEpoch);
        AppWidgetSingleton.getInstance().getTabsController().getTabModelTrainController().refreshModelInfo();
    }

    private void stopTraining(){
        cancel();
    }
}