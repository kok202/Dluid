package org.kok202.dluid.ai;

import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.entity.enumerator.Optimizer;
import org.kok202.dluid.ai.entity.enumerator.WeightInitWrapper;
import org.kok202.dluid.ai.listener.RunnableTrainingListener;
import org.kok202.dluid.ai.singleton.AIModelSingleton;
import org.kok202.dluid.ai.singleton.AIPropertiesSingleton;
import org.kok202.dluid.ai.singleton.structure.ManagedRecordSet;
import org.kok202.dluid.domain.structure.GraphManager;

import java.util.function.Consumer;

public class AIFacade {
    /*************************************************************************************************
     /* AI model
     *************************************************************************************************/
    public static void initializeModel(GraphManager<Layer> layerGraphManager){
        AIModelSingleton.getInstance().initialize(layerGraphManager);
    }

    public static void initializeTrainListener(int epochTaskPeriod, int batchTaskPeriod, Consumer epochTask, Consumer batchTask){
        AIModelSingleton.getInstance().setTrainListener(
                RunnableTrainingListener.builder()
                        .epochSize(AIPropertiesSingleton.getInstance().getTrainProperty().getEpoch())
                        .epochTaskPeriod(epochTaskPeriod)
                        .epochTask(epochTask)
                        .batchSize(AIPropertiesSingleton.getInstance().getTrainProperty().getBatchSize())
                        .totalRecordSize(AIPropertiesSingleton.getInstance().getTrainProperty().getTotalRecordSize())
                        .batchTaskPeriod(batchTaskPeriod)
                        .batchTask(batchTask)
                        .build());
    }

    public static void trainModel(){
        long layerId = 0; // FIXME
        AIModelSingleton.getInstance().train(
                AIPropertiesSingleton.getInstance().getTrainProperty().getDataSetManager(layerId).getManagedFeatureRecordSet().getNumericRecordSet(),
                AIPropertiesSingleton.getInstance().getTrainProperty().getDataSetManager(layerId).getManagedResultRecordSet().getNumericRecordSet());
    }

    /*************************************************************************************************
     /* AI model properties
     *************************************************************************************************/
    public static String getModelName(){
        return AIPropertiesSingleton.getInstance().getModelInfoProperty().getModelName();
    }

    public static void setModelName(String modelName){
        AIPropertiesSingleton.getInstance().getModelInfoProperty().setModelName(modelName);
    }

    public static int getModelLearnedEpochNumber(){
        return AIPropertiesSingleton.getInstance().getModelInfoProperty().getModelLearnedEpochNumber();
    }

    public static void setModelLearnedEpochNumber(int learnedEpochNumber){
        AIPropertiesSingleton.getInstance().getModelInfoProperty().setModelLearnedEpochNumber(learnedEpochNumber);
    }

    /*************************************************************************************************
     /* AI record data
     *************************************************************************************************/
    public static ManagedRecordSet getTrainFeatureSet(long inputLayerId){
        return AIPropertiesSingleton.getInstance()
                .getTrainProperty()
                .getDataSetManager(inputLayerId)
                .getManagedFeatureRecordSet();
    }

    public static ManagedRecordSet getTrainResultSet(long inputLayerId){
        return AIPropertiesSingleton.getInstance()
                .getTrainProperty()
                .getDataSetManager(inputLayerId)
                .getManagedResultRecordSet();
    }

    public static ManagedRecordSet getTestFeatureSet(){
        return AIPropertiesSingleton.getInstance()
                .getTestProperty()
                .getDataSetManager()
                .getManagedFeatureRecordSet();
    }

    public static ManagedRecordSet getTestResultSet(){
        return AIPropertiesSingleton.getInstance()
                .getTestProperty()
                .getDataSetManager()
                .getManagedResultRecordSet();
    }

    /*************************************************************************************************
     /* AI train property
     *************************************************************************************************/
    public static int getTrainTotalRecordSize(){
        return AIPropertiesSingleton.getInstance().getTrainProperty().getTotalRecordSize();
    }

    public static void setTrainTotalRecordSize(int totalRecordSize){
        AIPropertiesSingleton.getInstance().getTrainProperty().setTotalRecordSize(totalRecordSize);
    }

    public static int getTrainBatchSize(){
        return AIPropertiesSingleton.getInstance().getTrainProperty().getBatchSize();
    }

    public static void setTrainBatchSize(int batchSize){
        AIPropertiesSingleton.getInstance().getTrainProperty().setBatchSize(batchSize);
    }

    public static int getTrainEpoch(){
        return AIPropertiesSingleton.getInstance().getTrainProperty().getEpoch();
    }

    public static void setTrainEpoch(int epoch){
        AIPropertiesSingleton.getInstance().getTrainProperty().setEpoch(epoch);
    }

    public static double getTrainLearningRate() {
        return AIPropertiesSingleton.getInstance().getTrainProperty().getLearningRate();
    }

    public static void setTrainLearningRate(double learningRate) {
        AIPropertiesSingleton.getInstance().getTrainProperty().setLearningRate(learningRate);
    }

    public static WeightInitWrapper getTrainWeightInit() {
        return AIPropertiesSingleton.getInstance().getTrainProperty().getWeightInit();
    }

    public static void setTrainWeightInit(WeightInitWrapper weightInit) {
        AIPropertiesSingleton.getInstance().getTrainProperty().setWeightInit(weightInit);
    }

    public static Optimizer getTrainOptimizer() {
        return AIPropertiesSingleton.getInstance().getTrainProperty().getOptimizer();
    }

    public static void setTrainOptimizer(Optimizer optimizer) {
        AIPropertiesSingleton.getInstance().getTrainProperty().setOptimizer(optimizer);
    }

    /*************************************************************************************************
     /* AI test property
     *************************************************************************************************/

}
