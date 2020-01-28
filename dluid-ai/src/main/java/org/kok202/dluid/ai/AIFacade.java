package org.kok202.dluid.ai;

import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.entity.enumerator.BiasInitializer;
import org.kok202.dluid.ai.entity.enumerator.Optimizer;
import org.kok202.dluid.ai.entity.enumerator.WeightInitializer;
import org.kok202.dluid.ai.listener.TrainingEpochListener;
import org.kok202.dluid.ai.singleton.AISingleton;
import org.kok202.dluid.ai.singleton.structure.DataSetManager;
import org.kok202.dluid.ai.singleton.structure.ManagedRecordSet;
import org.kok202.dluid.domain.stream.NumericRecordSet;
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

    public static void initializeTrainListener(Consumer epochTask){
        AISingleton.getInstance().getModelManager().setTrainListener(
                TrainingEpochListener.builder()
                        .epochSize(AISingleton.getInstance().getModelManager().getModelParameter().getEpoch())
                        .epochTaskPeriod(AISingleton.getInstance().getModelManager().getModelParameter().getListeningPeriod())
                        .epochTask(epochTask)
                        .build());
    }

    public static void trainModel(){
        AISingleton.getInstance().getModelManager().train();
    }

    public static NumericRecordSet testModel(String inputLayerId, String targetResultLayerId){
        return AISingleton.getInstance().getModelManager().test(inputLayerId, targetResultLayerId);
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
    public static void remainFilterTrainDataManagerSet(List<String> inputLayerIds){
        Set<String> managedLayerIds = AISingleton.getInstance()
                .getTrainDataManager()
                .getDataSetManagerMap()
                .keySet();
        Set<String> deleteLayerIds = managedLayerIds
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

    public static Map<String, DataSetManager> getTrainDataSetManagerMap(){
        return AISingleton.getInstance()
                .getTrainDataManager()
                .getDataSetManagerMap();
    }

    public static ManagedRecordSet getTrainFeatureSet(String inputLayerId){
        return AISingleton.getInstance()
                .getTrainDataManager()
                .getDataSetManager(inputLayerId)
                .getManagedFeatureRecordSet();
    }

    public static ManagedRecordSet getTrainResultSet(String inputLayerId){
        return AISingleton.getInstance()
                .getTrainDataManager()
                .getDataSetManager(inputLayerId)
                .getManagedResultRecordSet();
    }

    /*************************************************************************************************
     /* AI train property
     *************************************************************************************************/

    public static int getListeningPeriod(){
        return AISingleton.getInstance().getModelManager().getModelParameter().getListeningPeriod();
    }

    public static void setListeningPeriod(int listeningPeriod){
        AISingleton.getInstance().getModelManager().getModelParameter().setListeningPeriod(listeningPeriod);
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

    public static int getTrainedEpoch() {
        return AISingleton.getInstance().getModelManager().getTrainedEpoch();
    }

    public static double getTrainLearningRate() {
        return AISingleton.getInstance().getModelManager().getModelParameter().getLearningRate();
    }

    public static void setTrainLearningRate(double learningRate) {
        AISingleton.getInstance().getModelManager().getModelParameter().setLearningRate(learningRate);
    }

    public static WeightInitializer getTrainWeightInitializer() {
        return AISingleton.getInstance().getModelManager().getModelParameter().getWeightInitializer();
    }

    public static BiasInitializer getTrainBiasInitializer() {
        return AISingleton.getInstance().getModelManager().getModelParameter().getBiasInitializer();
    }

    public static void setTrainWeightInitializer(WeightInitializer weightInitializer) {
        AISingleton.getInstance().getModelManager().getModelParameter().setWeightInitializer(weightInitializer);
    }

    public static void setTrainBiasInitializer(BiasInitializer biasInitializer) {
        AISingleton.getInstance().getModelManager().getModelParameter().setBiasInitializer(biasInitializer);
    }

    public static Optimizer getTrainOptimizer() {
        return AISingleton.getInstance().getModelManager().getModelParameter().getOptimizer();
    }

    public static void setTrainOptimizer(Optimizer optimizer) {
        AISingleton.getInstance().getModelManager().getModelParameter().setOptimizer(optimizer);
    }

    public static boolean isTrainingStop() {
        return AISingleton.getInstance().getModelManager().isTrainingStop();
    }

    public static void setTrainingStop(boolean trainingStop) {
        AISingleton.getInstance().getModelManager().setTrainingStop(trainingStop);
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
