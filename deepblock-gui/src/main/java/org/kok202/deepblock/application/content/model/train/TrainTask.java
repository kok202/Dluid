package org.kok202.deepblock.application.content.model.train;

import javafx.concurrent.Task;
import org.kok202.deepblock.ai.global.AIModelSingleton;
import org.kok202.deepblock.ai.global.AIPropertiesSingleton;
import org.kok202.deepblock.ai.helper.RunnableTrainingListener;
import org.kok202.deepblock.ai.helper.RunnableTrainingTaskContainer;
import org.kok202.deepblock.application.singleton.AppWidgetSingleton;
import org.kok202.deepblock.canvas.interfaces.CanvasInterface;
import org.kok202.deepblock.canvas.util.BlockNodeGraphUtil;

public class TrainTask extends Task<Integer> {
    private ModelTrainController modelTrainController;

    public void bindWithComponent(ModelTrainController modelTrainController) {
        this.modelTrainController = modelTrainController;
        this.modelTrainController.getProgressBarTrainingProgress().progressProperty().bind(this.progressProperty());
        this.modelTrainController.getButtonTrainingOneTime().setDisable(true);
        this.modelTrainController.getButtonTrainingNTime().setDisable(true);
        this.modelTrainController.getButtonTrainingStop().setDisable(false);
        this.modelTrainController.getButtonTrainingStop().setOnAction(event -> stopTraining());
    }

    @Override
    protected Integer call() throws Exception {
        modelTrainController.getTextAreaTrainingLog().clear();
        modelTrainController.getTextAreaTrainingLog().appendText("Try to create model.\n");
        AIModelSingleton.getInstance().initialize(
                BlockNodeGraphUtil.convertToLayerGraph(
                        CanvasInterface.findTrainInputGraphNode()));
        modelTrainController.getTextAreaTrainingLog().appendText("Try to create model. [done]\n");
        modelTrainController.getTextAreaTrainingLog().appendText("Try to add listener for print log.\n");

        int epochTaskPeriod = 1;
        int batchTaskPeriod =
                        AIPropertiesSingleton.getInstance().getTrainProperty().getTotalRecordSize() /
                        AIPropertiesSingleton.getInstance().getTrainProperty().getBatchSize() /
                        10;
        epochTaskPeriod = (epochTaskPeriod <= 0)? 1 : epochTaskPeriod;
        batchTaskPeriod = (batchTaskPeriod <= 0)? 1 : batchTaskPeriod;

        AIModelSingleton.getInstance().setTrainListener(
                RunnableTrainingListener.builder()
                        .epochSize(AIPropertiesSingleton.getInstance().getTrainProperty().getEpoch())
                        .epochTaskPeriod(epochTaskPeriod)
                        .epochTask((taskContainerObj) -> {
                            // not executed if epoch is not set when
                            RunnableTrainingTaskContainer taskContainer = (RunnableTrainingTaskContainer) taskContainerObj;
                            modelTrainController.getTextAreaTrainingLog().appendText("Epoch : " + taskContainer.getEpochCounter().getCount() + "\n");
                            modelTrainController.getTextAreaTrainingLog().appendText("Fitting score : " + taskContainer.getScore() + "\n");
                        })
                        .batchSize(AIPropertiesSingleton.getInstance().getTrainProperty().getBatchSize())
                        .totalRecordSize(AIPropertiesSingleton.getInstance().getTrainProperty().getTotalRecordSize())
                        .batchTaskPeriod(batchTaskPeriod)
                        .batchTask((taskContainerObj) -> {
                            RunnableTrainingTaskContainer taskContainer = (RunnableTrainingTaskContainer) taskContainerObj;
                            double currentProgress =
                                            taskContainer.getBatchCounter().getCount() +
                                            (taskContainer.getEpochCounter().getCount()-1) * taskContainer.getBatchCounter().getMax();
                            double totalProgress = taskContainer.getEpochCounter().getMax() * taskContainer.getBatchCounter().getMax();

                            int percent = (int)(currentProgress / totalProgress * 100);
                            percent = Math.max(0, Math.min(percent, 100));
                            updateProgress(percent, 100);
                            updateMessage(String.valueOf(taskContainer.getBatchCounter().calcPercent()));
                            modelTrainController.getTextAreaTrainingLog().appendText("Batch progress : " + taskContainer.getBatchCounter().calcPercent() + " / 100%\n");
                        })
                        .build());
        modelTrainController.getTextAreaTrainingLog().appendText("Try to add listener for print log. [done]\n");
        modelTrainController.getTextAreaTrainingLog().appendText("Training start.\n");
        AIModelSingleton.getInstance().train(
                AIPropertiesSingleton.getInstance().getTrainProperty().getDataSetManager().getManagedFeatureRecordSet().getNumericRecordSet(),
                AIPropertiesSingleton.getInstance().getTrainProperty().getDataSetManager().getManagedResultRecordSet().getNumericRecordSet());
        updateProgress(100, 100);
        updateMessage(String.valueOf(100));
        return 100;
    }

    @Override
    public void succeeded() {
        modelTrainController.getTextAreaTrainingLog().appendText("Training done.\n");
        modelTrainController.getButtonTrainingOneTime().setDisable(false);
        modelTrainController.getButtonTrainingNTime().setDisable(false);
        modelTrainController.getButtonTrainingStop().setDisable(true);
        int learnedEpoch = AIPropertiesSingleton.getInstance().getModelInfoProperty().getModelLearnedEpochNumber();
        int trainedEpoch = AIPropertiesSingleton.getInstance().getTrainProperty().getEpoch();
        AIPropertiesSingleton.getInstance().getModelInfoProperty().setModelLearnedEpochNumber(learnedEpoch + trainedEpoch);
        AppWidgetSingleton.getInstance().getContentRootController().getTabsController().getTabModelTrainController().refreshModelInfo();
    }

    private void stopTraining(){
        cancel();
    }
}