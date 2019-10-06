package org.kok202.deepblock.ai;

import org.deeplearning4j.datasets.iterator.impl.MnistDataSetIterator;
import org.deeplearning4j.nn.weights.WeightInit;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.ai.entity.enumerator.LayerType;
import org.kok202.deepblock.ai.entity.enumerator.Optimizer;
import org.kok202.deepblock.ai.global.AIModelSingleton;
import org.kok202.deepblock.ai.global.AIPropertiesSingleton;
import org.kok202.deepblock.ai.helper.NormalTrainingListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction;

public class DeeplearningMnist {
    public static void main(String[] args) throws Exception{
        AIPropertiesSingleton.getInstance().getTrainProperty().setBatchSize(128);
        AIPropertiesSingleton.getInstance().getTrainProperty().setTotalRecordSize(60000);
        AIPropertiesSingleton.getInstance().getTrainProperty().setOptimizer(Optimizer.ADAM);
        AIPropertiesSingleton.getInstance().getTrainProperty().setEpoch(1);
        AIPropertiesSingleton.getInstance().getTrainProperty().setLearningRate(0.006);
        AIPropertiesSingleton.getInstance().getTrainProperty().setWeightInit(WeightInit.XAVIER);
        AIPropertiesSingleton.getInstance().getTrainProperty().setLossFunction(LossFunction.NEGATIVELOGLIKELIHOOD);

        Layer layer1 = new Layer(LayerType.DENSE_LAYER);
        layer1.getProperties().setWeightInit(WeightInit.XAVIER);
        layer1.getProperties().setActivationFunction(Activation.RELU);
        layer1.getProperties().setInputSize(28 * 28);
        layer1.getProperties().setOutputSize(100);

        Layer layer2 = new Layer(LayerType.DENSE_LAYER);
        layer2.getProperties().setWeightInit(WeightInit.XAVIER);
        layer2.getProperties().setActivationFunction(Activation.RELU);
        layer2.getProperties().setInputSize(100);
        layer2.getProperties().setOutputSize(50);

        Layer layer3 = new Layer(LayerType.OUTPUT_LAYER);
        layer3.getProperties().setLossFunction(LossFunction.MSE);
        layer3.getProperties().setWeightInit(WeightInit.XAVIER);
        layer3.getProperties().setActivationFunction(Activation.SOFTMAX);
        layer3.getProperties().setInputSize(50);
        layer3.getProperties().setOutputSize(10);

        AIPropertiesSingleton.getInstance()
                .getModelProperty()
                .getLayerTree()
                .setRoot(layer1);
        AIPropertiesSingleton.getInstance()
                .getModelProperty()
                .getLayerTree()
                .getRoot()
                .attach(layer2);
        AIPropertiesSingleton.getInstance()
                .getModelProperty()
                .getLayerTree()
                .getRoot()
                .getChild(0)
                .attach(layer3);

        DataSetIterator mnistTrain = new MnistDataSetIterator(128, true, 123);
        DataSetIterator mnistTest = new MnistDataSetIterator(128, false, 123);

        AIModelSingleton.getInstance().initializeIfNull(
                AIPropertiesSingleton.getInstance()
                        .getModelProperty()
                        .getLayerTree());
        AIModelSingleton.getInstance().addTrainListener(
                NormalTrainingListener.builder()
                        .epochPrintPeriod(1)
                        .epochSize(AIPropertiesSingleton.getInstance().getTrainProperty().getEpoch())
                        .batchSize(AIPropertiesSingleton.getInstance().getTrainProperty().getBatchSize())
                        .batchPrintPeriod(10)
                        .totalRecordSize(AIPropertiesSingleton.getInstance().getTrainProperty().getTotalRecordSize())
                        .build());

        AIModelSingleton.getInstance().train(mnistTrain);
        System.out.println(AIModelSingleton.getInstance().test(mnistTest).stats());

    }
}
