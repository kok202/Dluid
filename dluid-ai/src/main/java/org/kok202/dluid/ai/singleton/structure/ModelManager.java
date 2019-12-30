package org.kok202.dluid.ai.singleton.structure;

import lombok.Getter;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.listener.TrainingEpochListener;
import org.kok202.dluid.ai.network.Model;
import org.kok202.dluid.ai.network.ModelParser;
import org.kok202.dluid.ai.singleton.AISingleton;
import org.kok202.dluid.ai.util.MultiDataSetIteratorUtil;
import org.kok202.dluid.domain.stream.NumericRecordSet;
import org.kok202.dluid.domain.structure.GraphManager;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.dataset.api.MultiDataSet;
import org.nd4j.linalg.dataset.api.iterator.MultiDataSetIterator;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class ModelManager {
    private ModelInformation modelInformation;
    private ModelParameter modelParameter;
    private List<Model> models;
    private TrainingEpochListener trainingEpochListener;

    public ModelManager(){
        modelInformation = new ModelInformation();
        modelParameter = new ModelParameter();
    }

    /*************************************************************************************************
     /* Initialize
     *************************************************************************************************/
    public void initialize(GraphManager<Layer> layerGraphManager) {
        models = ModelParser.parse(layerGraphManager);
    }

    public void setTrainListener(TrainingEpochListener trainingEpochListener){
        this.trainingEpochListener = trainingEpochListener;
    }

    /*************************************************************************************************
     /* Train
     *************************************************************************************************/
    public void train(){
        // Collect all input data set
        Map<Long, MultiDataSetIterator> multiDataSetIteratorMap = findAllInputLayerIds()
                .parallelStream()
                .collect(Collectors.toMap(
                    inputLayerId -> inputLayerId,
                    inputLayerId -> {
                        NumericRecordSet featureDataSet = AISingleton.getInstance().getTrainDataManager().getDataSetManager(inputLayerId).getManagedFeatureRecordSet().getNumericRecordSet();
                        NumericRecordSet resultDataSet = AISingleton.getInstance().getTrainDataManager().getDataSetManager(inputLayerId).getManagedFeatureRecordSet().getNumericRecordSet();
                        return MultiDataSetIteratorUtil.toMultiDataSetIterator(modelParameter.getBatchSize(), featureDataSet, resultDataSet);
                    }));

        // Train it alternately.
        for(int epoch = 0; epoch < modelParameter.getEpoch(); epoch++){
            multiDataSetIteratorMap.entrySet()
                    .parallelStream()
                    .forEach(entry -> entry.getValue().reset());

            while(true){
                long trainedDataSetNumber = multiDataSetIteratorMap.entrySet()
                        .stream()
                        .filter(entry -> entry.getValue().hasNext())
                        .map(entry -> {
                            long inputLayerId = entry.getKey();
                            MultiDataSet multiDataSet = entry.getValue().next();
                            findModel(inputLayerId).train(multiDataSet);
                            return true;
                        })
                        .count();

                if(trainedDataSetNumber == 0)
                    break;
            }

            if(trainingEpochListener != null)
                trainingEpochListener.epochCount();
        }
    }

    public void train(long inputLayerId, NumericRecordSet featureDataSet, NumericRecordSet resultDataSet){
        MultiDataSetIterator multiDataSetIterator = MultiDataSetIteratorUtil.toMultiDataSetIterator(modelParameter.getBatchSize(), featureDataSet, resultDataSet);
        findModel(inputLayerId).getTotalComputationGraph().fit(multiDataSetIterator, AISingleton.getInstance().getModelManager().getModelParameter().getEpoch());
    }

    /*************************************************************************************************
     /* Test
     *************************************************************************************************/
    public NumericRecordSet test(NumericRecordSet featureDataSet){
        return findTestModel().test(featureDataSet);
    }

    public Evaluation test(NumericRecordSet featureDataSet, NumericRecordSet resultDataSet){
        MultiDataSetIterator multiDataSetIterator = MultiDataSetIteratorUtil.toMultiDataSetIterator(modelParameter.getBatchSize(), featureDataSet, resultDataSet);
        return findTestModel().getTotalComputationGraph().evaluate(multiDataSetIterator);
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
