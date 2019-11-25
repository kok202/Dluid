package org.kok202.dluid.ai.singleton;

import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.optimize.api.TrainingListener;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.network.NetworkBuilder;
import org.kok202.dluid.ai.util.MultiDataSetIteratorUtil;
import org.kok202.dluid.domain.stream.NumericRecordSet;
import org.kok202.dluid.domain.structure.GraphManager;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.dataset.api.iterator.MultiDataSetIterator;

public class AIModelSingleton {

    private static class GlobalDataHolder{
        private static final AIModelSingleton instance = new AIModelSingleton();
    }

    public static AIModelSingleton getInstance(){
        return AIModelSingleton.GlobalDataHolder.instance;
    }

    private AIModelSingleton(){
    }

    private int prevLayerGraphHash;
    private ComputationGraph model;

    public void initialize(GraphManager<Layer> layerGraphManager) {
        int currLayerGraphHash = layerGraphManager.getHashCode();
        if(prevLayerGraphHash == currLayerGraphHash)
            return;
        prevLayerGraphHash = currLayerGraphHash;

        AIPropertiesSingleton
                .getInstance()
                .getModelLayersProperty()
                .setLayerGraphManager(layerGraphManager);
        model = NetworkBuilder.build();
        model.init();
    }

    public void setTrainListener(TrainingListener trainingListener){
        model.getListeners().clear();
        model.addListeners(trainingListener);
    }

    public void train(NumericRecordSet featureDataSet, NumericRecordSet resultDataSet){
        int batchSize = AIPropertiesSingleton
                .getInstance()
                .getTrainProperty()
                .getBatchSize();
        MultiDataSetIterator multiDataSetIterator = MultiDataSetIteratorUtil.toMultiDataSetIterator(batchSize, featureDataSet, resultDataSet);
        train(multiDataSetIterator);
    }

    public void train(MultiDataSetIterator multiDataSetIterator){
        model.fit(multiDataSetIterator, AIPropertiesSingleton.getInstance().getTrainProperty().getEpoch());
    }

    public Evaluation test(NumericRecordSet featureDataSet, NumericRecordSet resultDataSet){
        int batchSize = AIPropertiesSingleton
                .getInstance()
                .getTrainProperty()
                .getBatchSize();
        MultiDataSetIterator multiDataSetIterator = MultiDataSetIteratorUtil.toMultiDataSetIterator(batchSize, featureDataSet, resultDataSet);
        return test(multiDataSetIterator);
    }

    public Evaluation test(MultiDataSetIterator multiDataSetIterator){
        return model.evaluate(multiDataSetIterator);
    }
}
