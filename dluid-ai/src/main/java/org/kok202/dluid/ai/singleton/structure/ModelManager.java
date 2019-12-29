package org.kok202.dluid.ai.singleton.structure;

import lombok.Getter;
import org.deeplearning4j.optimize.api.TrainingListener;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.network.ModelParser;
import org.kok202.dluid.ai.singleton.AISingleton;
import org.kok202.dluid.ai.util.MultiDataSetIteratorUtil;
import org.kok202.dluid.domain.stream.NumericRecordSet;
import org.kok202.dluid.domain.structure.GraphManager;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.dataset.api.iterator.MultiDataSetIterator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class ModelManager {
    private ModelInformation modelInformation;
    private ModelParameter modelParameter;
    private List<Model> models;

    public ModelManager(){
        modelInformation = new ModelInformation();
        modelParameter = new ModelParameter();
    }

    /*************************************************************************************************
     /* Initialize
     *************************************************************************************************/
    public void initialize(GraphManager<Layer> layerGraphManager) {
        models = ModelParser.parse(layerGraphManager);
        models.forEach(model -> model.getComputationGraph().init());
    }

    public void setTrainListener(TrainingListener trainingListener){
        models.getListeners().clear();
        models.addListeners(trainingListener);
    }

    /*************************************************************************************************
     /* Train
     *************************************************************************************************/
    public void train(){
        // Collect all input data set
        List<Long> inputLayerIds = findAllInputLayerIds();
        Map<Long, NumericRecordSet> featureDataSetMap = new HashMap<>();
        Map<Long, NumericRecordSet> resultDataSetMap = new HashMap<>();

        inputLayerIds
                .parallelStream()
                .forEach(inputLayerId -> {
                    NumericRecordSet featureDataSet = AISingleton.getInstance().getTrainDataManager().getDataSetManager(inputLayerId).getManagedFeatureRecordSet().getNumericRecordSet();
                    NumericRecordSet resultDataSet = AISingleton.getInstance().getTrainDataManager().getDataSetManager(inputLayerId).getManagedFeatureRecordSet().getNumericRecordSet();
                    featureDataSetMap.put(inputLayerId, featureDataSet);
                    resultDataSetMap.put(inputLayerId, resultDataSet);
                });

        // Train it alternately.
        for(int epoch = 0; epoch < modelParameter.getEpoch(); epoch++){
            for(int )
        }
        train(inputLayerId, featureDataSet, resultDataSet);
    }

    public void train(long inputLayerId, NumericRecordSet featureDataSet, NumericRecordSet resultDataSet){
        MultiDataSetIterator multiDataSetIterator = MultiDataSetIteratorUtil.toMultiDataSetIterator(modelParameter.getBatchSize(), featureDataSet, resultDataSet);
        findModel(inputLayerId).getComputationGraph().fit(multiDataSetIterator, AISingleton.getInstance().getModelManager().getModelParameter().getEpoch());
    }

    /*************************************************************************************************
     /* Test
     *************************************************************************************************/
    public Evaluation test(NumericRecordSet featureDataSet, NumericRecordSet resultDataSet){
        MultiDataSetIterator multiDataSetIterator = MultiDataSetIteratorUtil.toMultiDataSetIterator(modelParameter.getBatchSize(), featureDataSet, resultDataSet);
        return findTestModel().getComputationGraph().evaluate(multiDataSetIterator);
    }

    /*************************************************************************************************
     /* ETC
     *************************************************************************************************/
    private List<Long> findAllInputLayerIds() {
        return models.stream()
                .map(Model::getInputLayerId)
                .collect(Collectors.toList());
    }

    private Model findModel(long inputLayerId){
        for (Model model : models){
            if(model.getInputLayerId() == inputLayerId)
                return model;
        }
        throw new RuntimeException("Can not find model which start from input layer : " + inputLayerId);
    }

    private Model findTestModel(){
        for (Model model : models){
            if(model.isTestModel())
                return model;
        }
        throw new RuntimeException("Can not find test model");
    }
}
