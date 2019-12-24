package org.kok202.dluid.ai.singleton.structure;

import lombok.Getter;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.optimize.api.TrainingListener;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.network.NetworkBuilder;
import org.kok202.dluid.ai.singleton.AISingleton;
import org.kok202.dluid.ai.util.MultiDataSetIteratorUtil;
import org.kok202.dluid.domain.stream.NumericRecordSet;
import org.kok202.dluid.domain.structure.GraphManager;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.dataset.api.iterator.MultiDataSetIterator;

@Getter
public class ModelManager {
    private ModelInformation modelInformation;
    private ModelParameter modelParameter;
    private GraphManager<Layer> layerGraphManager;
    private ComputationGraph model;

    public ModelManager(){
        modelInformation = new ModelInformation();
        modelParameter = new ModelParameter();
    }

    public void initialize(GraphManager<Layer> layerGraphManager) {
        this.layerGraphManager = layerGraphManager;
        model = NetworkBuilder.build();
        model.init();
    }

    public void setTrainListener(TrainingListener trainingListener){
        model.getListeners().clear();
        model.addListeners(trainingListener);
    }

    public void train(NumericRecordSet featureDataSet, NumericRecordSet resultDataSet){
        int batchSize = AISingleton
                .getInstance()
                .getModelManager()
                .getModelParameter()
                .getBatchSize();
        MultiDataSetIterator multiDataSetIterator = MultiDataSetIteratorUtil.toMultiDataSetIterator(batchSize, featureDataSet, resultDataSet);
        model.fit(multiDataSetIterator, AISingleton.getInstance().getModelManager().getModelParameter().getEpoch());
    }

    public Evaluation test(NumericRecordSet featureDataSet, NumericRecordSet resultDataSet){
        int batchSize = AISingleton
                .getInstance()
                .getModelManager()
                .getModelParameter()
                .getBatchSize();
        MultiDataSetIterator multiDataSetIterator = MultiDataSetIteratorUtil.toMultiDataSetIterator(batchSize, featureDataSet, resultDataSet);
        return model.evaluate(multiDataSetIterator);
    }
}
