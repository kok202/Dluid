package org.kok202.dluid.ai;

public class DeeplearningMnist {
    public static void main(String[] args) throws Exception{
//        AISingleton.getInstance().getTrainDataManager().setBatchSize(128);
//        AISingleton.getInstance().getTrainDataManager().setTotalRecordSize(60000);
//        AISingleton.getInstance().getTrainDataManager().setOptimizer(Optimizer.ADAM);
//        AISingleton.getInstance().getTrainDataManager().setEpoch(1);
//        AISingleton.getInstance().getTrainDataManager().setLearningRate(0.006);
//        AISingleton.getInstance().getTrainDataManager().setWeightInit(WeightInit.XAVIER);
//        AISingleton.getInstance().getTrainDataManager().setLossFunction(LossFunction.NEGATIVELOGLIKELIHOOD);
//
//        Layer layer0 = new Layer(LayerType.INPUT_LAYER);
//        Layer layer1 = new Layer(LayerType.DENSE_LAYER);
//        layer1.getProperties().setWeightInit(WeightInit.XAVIER);
//        layer1.getProperties().setActivationFunction(Activation.RELU);
//        layer1.getProperties().setInputSize(28 * 28);
//        layer1.getProperties().setOutputSize(100);
//
//        Layer layer2 = new Layer(LayerType.DENSE_LAYER);
//        layer2.getProperties().setWeightInit(WeightInit.XAVIER);
//        layer2.getProperties().setActivationFunction(Activation.RELU);
//        layer2.getProperties().setInputSize(100);
//        layer2.getProperties().setOutputSize(50);
//
//        Layer layer3 = new Layer(LayerType.OUTPUT_LAYER);
//        layer3.getProperties().setLossFunction(LossFunction.MSE);
//        layer3.getProperties().setWeightInit(WeightInit.XAVIER);
//        layer3.getProperties().setActivationFunction(Activation.SOFTMAX);
//        layer3.getProperties().setInputSize(50);
//        layer3.getProperties().setOutputSize(10);
//
//        AISingleton.getInstance()
//                .getModelLayersProperty()
//                .getLayerGraphManager()
//                .registerSoloNode(layer0);
//        AISingleton.getInstance()
//                .getModelLayersProperty()
//                .getLayerGraphManager()
//                .linkToNewData(layer0, layer1);
//        AISingleton.getInstance()
//                .getModelLayersProperty()
//                .getLayerGraphManager()
//                .linkToNewData(layer1, layer2);
//        AISingleton.getInstance()
//                .getModelLayersProperty()
//                .getLayerGraphManager()
//                .linkToNewData(layer2, layer3);
//
//        DataSetIterator mnistTrain = new MnistDataSetIterator(128, true, 123);
//        DataSetIterator mnistTest = new MnistDataSetIterator(128, false, 123);
//
//        ModelManager.getInstance().initialize(
//                AISingleton.getInstance()
//                        .getModelLayersProperty()
//                        .getLayerGraphManager());
//        ModelManager.getInstance().setTrainListener(
//                NormalTrainingListener.builder()
//                        .epochPrintPeriod(1)
//                        .epochSize(AISingleton.getInstance().getTrainDataManager().getEpoch())
//                        .batchSize(AISingleton.getInstance().getTrainDataManager().getBatchSize())
//                        .batchPrintPeriod(10)
//                        .totalRecordSize(AISingleton.getInstance().getTrainDataManager().getTotalRecordSize())
//                        .build());
//
//        ModelManager.getInstance().train(mnistTrain);
//        System.out.println(ModelManager.getInstance().test(mnistTest).stats());

    }
}
