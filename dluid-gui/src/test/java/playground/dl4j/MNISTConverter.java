package playground.dl4j;

import org.deeplearning4j.datasets.iterator.impl.MnistDataSetIterator;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

import java.util.ArrayList;
import java.util.List;

public class MNISTConverter {

    public static void main(String[] args) throws Exception {
        List<INDArray> trainFeatures = new ArrayList<>();
        List<INDArray> trainLabels = new ArrayList<>();

        int batchSize = 128;
        int batchNumber = 256;
        DataSetIterator iter = new MnistDataSetIterator(1,batchSize * batchNumber,false);
        while(iter.hasNext()){
            DataSet dataSet = iter.next();
            trainFeatures.add(dataSet.getFeatures());
            trainLabels.add(dataSet.getLabels());
        }

        System.out.println("trainFeature : " + trainFeatures.size());
        System.out.println("trainLabels : " + trainLabels.size());
        System.out.println("trainFeature : " + trainFeatures.get(0).length());
        System.out.println("trainLabels : " + trainLabels.get(0).length());
        System.out.println("trainFeature : " + trainFeatures.get(0));
        System.out.println("trainLabels : " + trainLabels.get(0));
    }
}
