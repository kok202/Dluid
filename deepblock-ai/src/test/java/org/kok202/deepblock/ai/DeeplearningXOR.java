package org.kok202.deepblock.ai;

import org.deeplearning4j.nn.weights.WeightInit;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.ai.entity.enumerator.LayerType;
import org.kok202.deepblock.ai.entity.enumerator.Optimizer;
import org.kok202.deepblock.ai.global.AIModelSingleton;
import org.kok202.deepblock.ai.global.AIPropertiesSingleton;
import org.kok202.deepblock.ai.helper.DataSetConverter;
import org.kok202.deepblock.ai.helper.NormalTrainingListener;
import org.kok202.deepblock.domain.stream.NumericRecordSet;
import org.kok202.deepblock.domain.stream.StringRecordSet;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction;

public class DeeplearningXOR {
    public static void main(String[] args) throws Exception{
        StringRecordSet dataSet = new StringRecordSet();
        dataSet.setHeader("a", "b");
        dataSet.addRecord("0", "0");
        dataSet.addRecord("0", "1");
        dataSet.addRecord("1", "0");
        dataSet.addRecord("1", "1");
        StringRecordSet resultSet = new StringRecordSet();
        resultSet.setHeader("xor(a,b)");
        resultSet.addRecord("0");
        resultSet.addRecord("1");
        resultSet.addRecord("1");
        resultSet.addRecord("0");
        NumericRecordSet trainFeature = NumericRecordSet.convertFrom(dataSet);
        NumericRecordSet trainResult = NumericRecordSet.convertFrom(resultSet);
        NumericRecordSet testFeature = NumericRecordSet.convertFrom(dataSet);
        NumericRecordSet testResult = NumericRecordSet.convertFrom(resultSet);
        DataSetConverter trainDataSetConverter = new DataSetConverter(trainFeature, trainResult);
        DataSetConverter testDataSetConverter = new DataSetConverter(testFeature, testResult);

        AIPropertiesSingleton.getInstance().getTrainProperty().setBatchSize(4);
        AIPropertiesSingleton.getInstance().getTrainProperty().setTotalRecordSize(4);
        AIPropertiesSingleton.getInstance().getTrainProperty().setOptimizer(Optimizer.ADAM);
        AIPropertiesSingleton.getInstance().getTrainProperty().setEpoch(1000);
        AIPropertiesSingleton.getInstance().getTrainProperty().setLearningRate(0.006);
        AIPropertiesSingleton.getInstance().getTrainProperty().setWeightInit(WeightInit.XAVIER);
        AIPropertiesSingleton.getInstance().getTrainProperty().setLossFunction(LossFunction.NEGATIVELOGLIKELIHOOD);

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
                .getLayerTree()
                .setRoot(layer1);
        AIPropertiesSingleton.getInstance()
                .getModelLayersProperty()
                .getLayerTree()
                .getRoot()
                .attach(layer2);

        AIModelSingleton.getInstance().initialize(
                AIPropertiesSingleton.getInstance()
                        .getModelLayersProperty()
                        .getLayerTree());
        AIModelSingleton.getInstance().addTrainListener(
                NormalTrainingListener.builder()
                        .epochPrintPeriod(100)
                        .epochSize(AIPropertiesSingleton.getInstance().getTrainProperty().getEpoch())
                        .batchSize(AIPropertiesSingleton.getInstance().getTrainProperty().getBatchSize())
                        .batchPrintPeriod(1)
                        .totalRecordSize(AIPropertiesSingleton.getInstance().getTrainProperty().getTotalRecordSize())
                        .build());
        AIModelSingleton.getInstance().train(trainDataSetConverter.toDataSet());
        System.out.println(AIModelSingleton.getInstance().test(testDataSetConverter.toDataSet()).stats());

    }
}
