package org.kok202.dluid.application.content.train;

import javafx.concurrent.Task;
import org.kok202.dluid.CanvasFacade;
import org.kok202.dluid.ai.AIFacade;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.listener.RunnableTrainingTaskContainer;
import org.kok202.dluid.application.singleton.AppWidgetSingleton;
import org.kok202.dluid.canvas.util.BlockNodeGraphUtil;
import org.kok202.dluid.domain.structure.GraphManager;

public class TrainTask extends Task<Integer> {
    private ModelTrainTaskController modelTrainTaskController;

    public void bindWithComponent(ModelTrainTaskController modelTrainTaskController) {
        this.modelTrainTaskController = modelTrainTaskController;
        this.modelTrainTaskController.getProgressBarTrainingProgress().progressProperty().bind(this.progressProperty());
        this.modelTrainTaskController.getButtonTrainingOneTime().setDisable(true);
        this.modelTrainTaskController.getButtonTrainingNTime().setDisable(true);
        this.modelTrainTaskController.getButtonTrainingStop().setDisable(false);
        this.modelTrainTaskController.getButtonTrainingStop().setOnAction(event -> stopTraining());
    }

    @Override
    protected Integer call() throws Exception {
        modelTrainTaskController.getTextAreaTrainingLog().clear();
        modelTrainTaskController.getTextAreaTrainingLog().appendText("Try to create model.\n");
        GraphManager<Layer> layerGraphManager = BlockNodeGraphUtil.convertToLayerGraph(CanvasFacade.findTrainInputGraphNode());
        AIFacade.initializeModel(layerGraphManager);
        modelTrainTaskController.getTextAreaTrainingLog().appendText("Try to create model. [done]\n");

        modelTrainTaskController.getTextAreaTrainingLog().appendText("Try to add listener for print log.\n");
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
                    modelTrainTaskController.getTextAreaTrainingLog().appendText("Epoch : " + taskContainer.getEpochCounter().getCount() + "\n");
                    modelTrainTaskController.getTextAreaTrainingLog().appendText("Fitting score : " + taskContainer.getScore() + "\n");
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
                    modelTrainTaskController.getTextAreaTrainingLog().appendText("Batch progress : " + taskContainer.getBatchCounter().calcPercent() + " / 100%\n");
                });
        modelTrainTaskController.getTextAreaTrainingLog().appendText("Try to add listener for print log. [done]\n");
        modelTrainTaskController.getTextAreaTrainingLog().appendText("Training start.\n");
        AIFacade.trainModel();
        updateProgress(100, 100);
        updateMessage(String.valueOf(100));
        return 100;
    }

    @Override
    public void succeeded() {
        modelTrainTaskController.getTextAreaTrainingLog().appendText("Training done.\n");
        modelTrainTaskController.getButtonTrainingOneTime().setDisable(false);
        modelTrainTaskController.getButtonTrainingNTime().setDisable(false);
        modelTrainTaskController.getButtonTrainingStop().setDisable(true);
        int learnedEpoch = AIFacade.getModelLearnedEpochNumber();
        int trainedEpoch = AIFacade.getTrainEpoch();
        AIFacade.setModelLearnedEpochNumber(learnedEpoch + trainedEpoch);
        AppWidgetSingleton.getInstance().getContentRootController().getTabsController().getTabModelTrainController().refreshModelInfo();
    }

    private void stopTraining(){
        cancel();
    }
}