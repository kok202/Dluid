package org.kok202.dluid.ai;

import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.entity.enumerator.ActivationWrapper;
import org.kok202.dluid.ai.entity.enumerator.LayerType;
import org.kok202.dluid.ai.entity.enumerator.Optimizer;
import org.kok202.dluid.ai.entity.enumerator.WeightInitWrapper;
import org.kok202.dluid.ai.listener.NormalTrainingListener;
import org.kok202.dluid.ai.singleton.AISingleton;
import org.kok202.dluid.domain.stream.NumericRecordSet;
import org.kok202.dluid.domain.stream.StringRecordSet;
import org.kok202.dluid.domain.structure.GraphManager;
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

        AISingleton.getInstance().getModelManager().getModelParameter().setBatchSize(4);
        AISingleton.getInstance().getModelManager().getModelParameter().setTotalRecordSize(4);
        AISingleton.getInstance().getModelManager().getModelParameter().setOptimizer(Optimizer.ADAM);
        AISingleton.getInstance().getModelManager().getModelParameter().setEpoch(1000);
        AISingleton.getInstance().getModelManager().getModelParameter().setLearningRate(0.006);
        AISingleton.getInstance().getModelManager().getModelParameter().setWeightInit(WeightInitWrapper.XAVIER);
        AISingleton.getInstance().getModelManager().getModelParameter().setLossFunction(LossFunction.NEGATIVELOGLIKELIHOOD);

        Layer layer0 = new Layer(LayerType.INPUT_LAYER);
        Layer layer1 = new Layer(LayerType.DENSE_LAYER);
        layer1.getProperties().setWeightInit(WeightInitWrapper.XAVIER);
        layer1.getProperties().setActivationFunction(ActivationWrapper.SIGMOID);
        layer1.getProperties().setInputSize(2);
        layer1.getProperties().setOutputSize(10);

        Layer layer2 = new Layer(LayerType.OUTPUT_LAYER);
        layer2.getProperties().setLossFunction(LossFunction.MSE);
        layer2.getProperties().setWeightInit(WeightInitWrapper.XAVIER);
        layer2.getProperties().setActivationFunction(ActivationWrapper.SIGMOID);
        layer2.getProperties().setInputSize(10);
        layer2.getProperties().setOutputSize(1);

        GraphManager<Layer> layerGraphManager = new GraphManager<>();
        layerGraphManager.registerSoloNode(layer0);
        layerGraphManager.linkToNewData(layer0, layer1);
        layerGraphManager.linkToNewData(layer1, layer2);

        AISingleton.getInstance()
                .getModelManager()
                .initialize(layerGraphManager);
        AISingleton.getInstance()
                .getModelManager()
                .setTrainListener(NormalTrainingListener.builder()
                        .epochPrintPeriod(100)
                        .epochSize(AISingleton.getInstance().getModelManager().getModelParameter().getEpoch())
                        .batchSize(AISingleton.getInstance().getModelManager().getModelParameter().getBatchSize())
                        .batchPrintPeriod(1)
                        .totalRecordSize(AISingleton.getInstance().getModelManager().getModelParameter().getTotalRecordSize())
                        .build());
        AISingleton.getInstance().getModelManager().train(layer0.getId(), trainFeature, trainResult);
        System.out.println(AISingleton.getInstance().getModelManager().test(testFeature, testResult).stats());

    }
}
