package org.kok202.dluid.application.content.train;

import javafx.concurrent.Task;
import org.kok202.dluid.AppFacade;
import org.kok202.dluid.ai.AIFacade;
import org.kok202.dluid.ai.listener.TrainingEpochContainer;
import org.kok202.dluid.application.singleton.AppWidgetSingleton;
import org.kok202.dluid.model.ModelStateManager;

public class TrainTask extends Task<TrainProgressContainer> {

    public void bindWithComponent(ModelTrainTaskController modelTrainTaskController) {
        modelTrainTaskController.getButtonTrainingStop().setOnAction(event -> stopTraining());
        valueProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue == null)
                return;
            if(newValue.isExistMessage())
                modelTrainTaskController.getTextAreaTrainingLog().appendText(newValue.getMessage() + "\n");
            if(newValue.isExistEpoch() && newValue.isExistScore())
                modelTrainTaskController.getLineChartAdapter().appendData(newValue.getEpoch(), newValue.getScore());
            if(newValue.isExistProgress())
                modelTrainTaskController.getProgressBarTrainingProgress().setProgress(newValue.getProgress());
        }));
        AppFacade.setTrainingButtonDisable(false);
    }

    @Override
    protected TrainProgressContainer call() throws Exception {
        validateTrainPossible();
        setTrainListener();
        trainModel();
        return null;
    }

    private void validateTrainPossible(){
        updateValue(new TrainProgressContainer("Check training possible."));
        ModelStateManager.validateTrainPossible();
        updateValue(new TrainProgressContainer("Check training possible. [Successful]"));
    }

    private void setTrainListener(){
        updateValue(new TrainProgressContainer("Try to add listener for print log."));
        int epochTaskPeriod = 1;
        AIFacade.initializeTrainListener(
                epochTaskPeriod,
                (taskContainerObj) -> {
                    // not executed if epoch is not set when
                    TrainingEpochContainer trainingEpochContainer = (TrainingEpochContainer) taskContainerObj;
                    int epochCount = trainingEpochContainer.getEpochCounter().getCount();
                    double fittingScore = trainingEpochContainer.getScore();

                    TrainProgressContainer trainProgressContainer = new TrainProgressContainer();
                    trainProgressContainer.setEpoch(epochCount);
                    trainProgressContainer.setScore(fittingScore);
                    trainProgressContainer.setProgress(trainingEpochContainer.getProgress());
                    trainProgressContainer.setMessage(String.format("Epoch : %d, Fitting score : %f", epochCount, fittingScore));
                    updateValue(trainProgressContainer);
                });
        updateValue(new TrainProgressContainer("Try to add listener for print log. [done]"));
    }

    private void trainModel(){
        updateValue(new TrainProgressContainer("Training start."));
        AIFacade.trainModel();
    }

    private void stopTraining(){
        cancel();
    }

    @Override
    public void succeeded() {
        updateValue(new TrainProgressContainer("Training done."));
        int learnedEpoch = AIFacade.getModelLearnedEpochNumber();
        int trainedEpoch = AIFacade.getTrainEpoch();
        AIFacade.setModelLearnedEpochNumber(learnedEpoch + trainedEpoch);
        AppWidgetSingleton.getInstance().getTabsController().getTabModelTrainController().getModelInformationController().refreshModelInformation();
        AppFacade.setTrainingButtonDisable(false);
    }

    @Deprecated
    private void testTask() throws InterruptedException {
        TrainProgressContainer trainProgressContainer = new TrainProgressContainer();
        trainProgressContainer.setEpoch(0);
        trainProgressContainer.setScore(1.0);
        trainProgressContainer.setProgress(0.1);
        trainProgressContainer.setMessage(String.format("Epoch : %d, Fitting score : %f", 1, 1.0));
        updateValue(trainProgressContainer);
        Thread.sleep(1000);
        trainProgressContainer = new TrainProgressContainer();
        trainProgressContainer.setEpoch(1);
        trainProgressContainer.setScore(0.2);
        trainProgressContainer.setProgress(0.6);
        trainProgressContainer.setMessage(String.format("Epoch : %d, Fitting score : %f", 2, 0.2));
        updateValue(trainProgressContainer);
        Thread.sleep(1000);
        trainProgressContainer = new TrainProgressContainer();
        trainProgressContainer.setEpoch(2);
        trainProgressContainer.setScore(0.05);
        trainProgressContainer.setProgress(1);
        trainProgressContainer.setMessage(String.format("Epoch : %d, Fitting score : %f", 3, 0.05));
        updateValue(trainProgressContainer);
        Thread.sleep(1000);
    }

}