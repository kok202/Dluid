package org.kok202.deepblock.application.content.model.train;

import javafx.concurrent.Task;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.ai.facade.AIFacade;
import org.kok202.deepblock.ai.listener.RunnableTrainingTaskContainer;
import org.kok202.deepblock.application.singleton.AppWidgetSingleton;
import org.kok202.deepblock.canvas.facade.CanvasFacade;
import org.kok202.deepblock.canvas.util.BlockNodeGraphUtil;
import org.kok202.deepblock.domain.structure.GraphManager;

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
        GraphManager<Layer> layerGraphManager = BlockNodeGraphUtil.convertToLayerGraph(CanvasFacade.findTrainInputGraphNode());
        AIFacade.initializeModel(layerGraphManager);
        modelTrainController.getTextAreaTrainingLog().appendText("Try to create model. [done]\n");

        modelTrainController.getTextAreaTrainingLog().appendText("Try to add listener for print log.\n");
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
                    modelTrainController.getTextAreaTrainingLog().appendText("Epoch : " + taskContainer.getEpochCounter().getCount() + "\n");
                    modelTrainController.getTextAreaTrainingLog().appendText("Fitting score : " + taskContainer.getScore() + "\n");
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
                    modelTrainController.getTextAreaTrainingLog().appendText("Batch progress : " + taskContainer.getBatchCounter().calcPercent() + " / 100%\n");
                });
        modelTrainController.getTextAreaTrainingLog().appendText("Try to add listener for print log. [done]\n");
        modelTrainController.getTextAreaTrainingLog().appendText("Training start.\n");
        AIFacade.trainModel();
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
        int learnedEpoch = AIFacade.getModelLearnedEpochNumber();
        int trainedEpoch = AIFacade.getTrainEpoch();
        AIFacade.setModelLearnedEpochNumber(learnedEpoch + trainedEpoch);
        AppWidgetSingleton.getInstance().getContentRootController().getTabsController().getTabModelTrainController().refreshModelInfo();
    }

    private void stopTraining(){
        cancel();
    }
}