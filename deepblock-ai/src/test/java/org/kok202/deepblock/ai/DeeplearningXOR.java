package org.kok202.deepblock.ai;

import org.deeplearning4j.nn.weights.WeightInit;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.ai.entity.enumerator.LayerType;
import org.kok202.deepblock.ai.entity.enumerator.Optimizer;
import org.kok202.deepblock.ai.listener.NormalTrainingListener;
import org.kok202.deepblock.ai.singleton.AIModelSingleton;
import org.kok202.deepblock.ai.singleton.AIPropertiesSingleton;
import org.kok202.deepblock.domain.stream.NumericRecordSet;
import org.kok202.deepblock.domain.stream.StringRecordSet;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction;

public class DeeplearningXOR {
    public static void main(String[] args) throws Exception{
        StringRecordSet featureSet = new StringRecordSet();
        featureSet.setHeader("a", "b");
        featureSet.addRecord("0", "0");
        featureSet.addRecord("0", "1");
        featureSet.addRecord("1", "0");
        featureSet.addRecord("1", "1");
        StringRecordSet resultSet = new StringRecordSet();
        resultSet.setHeader("xor(a,b)");
        resultSet.addRecord("0");
        resultSet.addRecord("1");
        resultSet.addRecord("1");
        resultSet.addRecord("0");
        NumericRecordSet trainFeature = NumericRecordSet.convertFrom(featureSet);
        NumericRecordSet trainResult = NumericRecordSet.convertFrom(resultSet);
        NumericRecordSet testFeature = NumericRecordSet.convertFrom(featureSet);
        NumericRecordSet testResult = NumericRecordSet.convertFrom(resultSet);

        AIPropertiesSingleton.getInstance().getTrainProperty().setBatchSize(4);
        AIPropertiesSingleton.getInstance().getTrainProperty().setTotalRecordSize(4);
        AIPropertiesSingleton.getInstance().getTrainProperty().setOptimizer(Optimizer.ADAM);
        AIPropertiesSingleton.getInstance().getTrainProperty().setEpoch(1000);
        AIPropertiesSingleton.getInstance().getTrainProperty().setLearningRate(0.006);
        AIPropertiesSingleton.getInstance().getTrainProperty().setWeightInit(WeightInit.XAVIER);
        AIPropertiesSingleton.getInstance().getTrainProperty().setLossFunction(LossFunction.NEGATIVELOGLIKELIHOOD);

        Layer layer0 = new Layer(LayerType.INPUT_LAYER);
        Layer layer1 = new Layer(LayerType.DENSE_LAYER);
        layer1.getProperties().setWeightInit(WeightInit.XAVIER);
        layer1.getProperties().setActivationFunction(Activation.SIGMOID);
        layer1.getProperties().setInputSize(2);
        layer1.getProperties().setOutputSize(10);

        Layer layer2 = new Layer(LayerType.OUTPUT_LAYER);
        layer2.getProperties().setLossFunction(LossFunction.MSE);
        layer2.getProperties().setWeightInit(WeightInit.XAVIER);
        layer2.getProperties().setActivationFunction(Activation.SIGMOID);
        layer2.getProperties().setInputSize(10);
        layer2.getProperties().setOutputSize(1);

        AIPropertiesSingleton.getInstance()
                .getModelLayersProperty()
                .getLayerGraphManager()
                .registerSoloNode(layer0);
        AIPropertiesSingleton.getInstance()
                .getModelLayersProperty()
                .getLayerGraphManager()
                .linkToNewData(layer0, layer1);
        AIPropertiesSingleton.getInstance()
                .getModelLayersProperty()
                .getLayerGraphManager()
                .linkToNewData(layer1, layer2);

        AIModelSingleton.getInstance().initialize(
                AIPropertiesSingleton.getInstance()
                        .getModelLayersProperty()
                        .getLayerGraphManager());
        AIModelSingleton.getInstance().setTrainListener(
                NormalTrainingListener.builder()
                        .epochPrintPeriod(100)
                        .epochSize(AIPropertiesSingleton.getInstance().getTrainProperty().getEpoch())
                        .batchSize(AIPropertiesSingleton.getInstance().getTrainProperty().getBatchSize())
                        .batchPrintPeriod(1)
                        .totalRecordSize(AIPropertiesSingleton.getInstance().getTrainProperty().getTotalRecordSize())
                        .build());
        AIModelSingleton.getInstance().train(trainFeature, trainResult);
        System.out.println(AIModelSingleton.getInstance().test(testFeature, testResult).stats());

    }
}
