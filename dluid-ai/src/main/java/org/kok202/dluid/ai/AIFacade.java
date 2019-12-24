package org.kok202.dluid.ai;

import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.entity.enumerator.Optimizer;
import org.kok202.dluid.ai.entity.enumerator.WeightInitWrapper;
import org.kok202.dluid.ai.listener.RunnableTrainingListener;
import org.kok202.dluid.ai.singleton.AISingleton;
import org.kok202.dluid.ai.singleton.structure.DataSetManager;
import org.kok202.dluid.ai.singleton.structure.ManagedRecordSet;
import org.kok202.dluid.domain.structure.GraphManager;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class AIFacade {
    /*************************************************************************************************
     /* AI model
     *************************************************************************************************/
    public static void initializeModel(GraphManager<Layer> layerGraphManager){
        AISingleton.getInstance().getModelManager().initialize(layerGraphManager);
    }

    public static void initializeTrainListener(int epochTaskPeriod, int batchTaskPeriod, Consumer epochTask, Consumer batchTask){
        AISingleton.getInstance().getModelManager().setTrainListener(
                RunnableTrainingListener.builder()
                        .epochSize(AISingleton.getInstance().getModelManager().getModelParameter().getEpoch())
                        .epochTaskPeriod(epochTaskPeriod)
                        .epochTask(epochTask)
                        .batchSize(AISingleton.getInstance().getModelManager().getModelParameter().getBatchSize())
                        .totalRecordSize(AISingleton.getInstance().getModelManager().getModelParameter().getTotalRecordSize())
                        .batchTaskPeriod(batchTaskPeriod)
                        .batchTask(batchTask)
                        .build());
    }

    public static void trainModel(){
        long layerId = 0; // FIXME
        AISingleton.getInstance().getModelManager().train(
                AISingleton.getInstance().getTrainDataManager().getDataSetManager(layerId).getManagedFeatureRecordSet().getNumericRecordSet(),
                AISingleton.getInstance().getTrainDataManager().getDataSetManager(layerId).getManagedResultRecordSet().getNumericRecordSet());
    }

    public static String getModelName(){
        return  AISingleton.getInstance().getModelManager().getModelInformation().getModelName();
    }

    public static void setModelName(String modelName){
        AISingleton.getInstance().getModelManager().getModelInformation().setModelName(modelName);
    }

    public static int getModelLearnedEpochNumber(){
        return  AISingleton.getInstance().getModelManager().getModelInformation().getModelLearnedEpochNumber();
    }

    public static void setModelLearnedEpochNumber(int learnedEpochNumber){
        AISingleton.getInstance().getModelManager().getModelInformation().setModelLearnedEpochNumber(learnedEpochNumber);
    }

    /*************************************************************************************************
     /* AI train data
     *************************************************************************************************/
    public static void remainFilterTrainDataManagerSet(List<Long> inputLayerIds){
        Set<Long> managedLayerIds = AISingleton.getInstance()
                .getTrainDataManager()
                .getDataSetManagerMap()
                .keySet();
        Set<Long> deleteLayerIds = managedLayerIds
                .stream()
                .filter(inputLayerId -> !inputLayerIds.contains(inputLayerId))
                .collect(Collectors.toSet());
        deleteLayerIds
                .forEach(deleteLayerId -> {
                    AISingleton.getInstance()
                            .getTrainDataManager()
                            .getDataSetManagerMap()
                            .remove(deleteLayerId);
                });
        inputLayerIds
                .forEach(inputLayerId -> {
                    AISingleton.getInstance()
                            .getTrainDataManager()
                            .createDataSetManager(inputLayerId);
                });
    }

    public static Map<Long, DataSetManager> getTrainDataSetManagerMap(){
        return AISingleton.getInstance()
                .getTrainDataManager()
                .getDataSetManagerMap();
    }

    public static ManagedRecordSet getTrainFeatureSet(long inputLayerId){
        return AISingleton.getInstance()
                .getTrainDataManager()
                .getDataSetManager(inputLayerId)
                .getManagedFeatureRecordSet();
    }

    public static ManagedRecordSet getTrainResultSet(long inputLayerId){
        return AISingleton.getInstance()
                .getTrainDataManager()
                .getDataSetManager(inputLayerId)
                .getManagedResultRecordSet();
    }

    /*************************************************************************************************
     /* AI train property
     *************************************************************************************************/
    public static int getTrainTotalRecordSize(){
        return AISingleton.getInstance().getModelManager().getModelParameter().getTotalRecordSize();
    }

    public static void setTrainTotalRecordSize(int totalRecordSize){
        AISingleton.getInstance().getModelManager().getModelParameter().setTotalRecordSize(totalRecordSize);
    }

    public static int getTrainBatchSize(){
        return AISingleton.getInstance().getModelManager().getModelParameter().getBatchSize();
    }

    public static void setTrainBatchSize(int batchSize){
        AISingleton.getInstance().getModelManager().getModelParameter().setBatchSize(batchSize);
    }

    public static int getTrainEpoch(){
        return AISingleton.getInstance().getModelManager().getModelParameter().getEpoch();
    }

    public static void setTrainEpoch(int epoch){
        AISingleton.getInstance().getModelManager().getModelParameter().setEpoch(epoch);
    }

    public static double getTrainLearningRate() {
        return AISingleton.getInstance().getModelManager().getModelParameter().getLearningRate();
    }

    public static void setTrainLearningRate(double learningRate) {
        AISingleton.getInstance().getModelManager().getModelParameter().setLearningRate(learningRate);
    }

    public static WeightInitWrapper getTrainWeightInit() {
        return AISingleton.getInstance().getModelManager().getModelParameter().getWeightInit();
    }

    public static void setTrainWeightInit(WeightInitWrapper weightInit) {
        AISingleton.getInstance().getModelManager().getModelParameter().setWeightInit(weightInit);
    }

    public static Optimizer getTrainOptimizer() {
        return AISingleton.getInstance().getModelManager().getModelParameter().getOptimizer();
    }

    public static void setTrainOptimizer(Optimizer optimizer) {
        AISingleton.getInstance().getModelManager().getModelParameter().setOptimizer(optimizer);
    }

    /*************************************************************************************************
     /* AI test data
     *************************************************************************************************/

    public static ManagedRecordSet getTestFeatureSet(){
        return AISingleton.getInstance()
                .getTestDataSetManager()
                .getManagedFeatureRecordSet();
    }

    public static ManagedRecordSet getTestResultSet(){
        return AISingleton.getInstance()
                .getTestDataSetManager()
                .getManagedResultRecordSet();
    }

    /*************************************************************************************************
     /* AI test property
     *************************************************************************************************/


}
