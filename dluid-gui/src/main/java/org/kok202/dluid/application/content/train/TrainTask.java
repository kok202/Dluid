package org.kok202.dluid.application.content.train;

import javafx.concurrent.Task;
import org.kok202.dluid.AppFacade;
import org.kok202.dluid.ai.AIFacade;
import org.kok202.dluid.ai.listener.TrainingEpochContainer;
import org.kok202.dluid.application.singleton.AppWidgetSingleton;
import org.kok202.dluid.model.ModelStateManager;

public class TrainTask extends Task<TrainProgressContainer> {

    public void bindWithComponent(ModelTrainTaskController modelTrainTaskController) {
        modelTrainTaskController.getButtonTrainingStop().setOnAction(event -> cancel());
        valueProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue == null)
                return;
            if(newValue.isExistMessage())
                modelTrainTaskController.getTextAreaTrainingLog().appendText(newValue.getMessage() + "\n");
            if(newValue.isExistEpoch() && newValue.isExistScore())
                modelTrainTaskController.getLineChartAdapter().addData(newValue.getEpoch(), newValue.getScore());
            if(newValue.isExistProgress())
                modelTrainTaskController.getProgressBarTrainingProgress().setProgress(newValue.getProgress());
        }));
        AppFacade.setTrainingButtonDisable(false);
    }

    @Override
    protected TrainProgressContainer call() {
        updateValue(new TrainProgressContainer("Check training possible."));
        ModelStateManager.validateTrainPossible();
        updateValue(new TrainProgressContainer("Check training possible. [Successful]"));
        updateValue(new TrainProgressContainer("Try to add listener for print log."));
        final int learnedEpoch = AIFacade.getModelLearnedEpochNumber();
        AIFacade.initializeTrainListener(
                (taskContainerObj) -> {
                    TrainingEpochContainer trainingEpochContainer = (TrainingEpochContainer) taskContainerObj;
                    TrainProgressContainer trainProgressContainer = new TrainProgressContainer();
                    trainProgressContainer.setEpoch(learnedEpoch + trainingEpochContainer.getEpochCounter().getCount());
                    trainProgressContainer.setScore(trainingEpochContainer.getScore());
                    trainProgressContainer.setProgress(trainingEpochContainer.getProgress());
                    trainProgressContainer.setMessage(String.format("Epoch : %d, Fitting score : %f", trainProgressContainer.getEpoch(), trainProgressContainer.getScore()));
                    updateValue(trainProgressContainer);
                });
        updateValue(new TrainProgressContainer("Try to add listener for print log. [done]"));
        updateValue(new TrainProgressContainer("Training start."));
        AppFacade.setTrainingButtonDisable(true);
        AIFacade.trainModel();
        return null;
    }

    @Override
    public void succeeded() {
        AppFacade.setTrainingButtonDisable(false);
        updateValue(new TrainProgressContainer("Training done."));
        int learnedEpoch = AIFacade.getModelLearnedEpochNumber();
        int trainedEpoch = AIFacade.getTrainEpoch();
        AIFacade.setModelLearnedEpochNumber(learnedEpoch + trainedEpoch);
        AppWidgetSingleton.getInstance().getTabsController().getTabModelTrainController().getModelInformationController().refreshModelInformation();
    }

    @Override
    public void cancelled() {
        AppFacade.setTrainingButtonDisable(false);
    }

}