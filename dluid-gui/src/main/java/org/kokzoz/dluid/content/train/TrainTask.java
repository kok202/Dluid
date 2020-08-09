package org.kokzoz.dluid.content.train;

import javafx.concurrent.Task;
import org.kokzoz.dluid.AppFacade;
import org.kokzoz.dluid.ai.AIFacade;
import org.kokzoz.dluid.ai.listener.TrainingEpochContainer;
import org.kokzoz.dluid.domain.action.ActionType;
import org.kokzoz.dluid.model.ModelStateManager;

public class TrainTask extends Task<TrainProgressContainer> {

    public void bindWithComponent(ModelTrainTaskController modelTrainTaskController) {
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
        AppFacade.dispatchAction(ActionType.REFRESH_TRAINING_BUTTON_ENABLE);
    }

    @Override
    protected TrainProgressContainer call() {
        AppFacade.dispatchAction(ActionType.REFRESH_TRAINING_BUTTON_DISABLE);
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
        AIFacade.trainModel();
        return null;
    }

    @Override
    public void succeeded() {
        updateValue(new TrainProgressContainer("Training done."));
        int learnedEpoch = AIFacade.getModelLearnedEpochNumber();
        int trainedEpoch = AIFacade.getTrainedEpoch();
        AIFacade.setModelLearnedEpochNumber(learnedEpoch + trainedEpoch);
        AppFacade.dispatchAction(ActionType.REFRESH_TRAINING_BUTTON_ENABLE);
        AppFacade.dispatchAction(ActionType.REFRESH_MODEL_INFORMATION);
    }

    @Override
    protected void failed() {
        AppFacade.dispatchAction(ActionType.REFRESH_TRAINING_BUTTON_ENABLE);
    }

}