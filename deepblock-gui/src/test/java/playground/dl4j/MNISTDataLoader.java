package playground.dl4j;

import org.deeplearning4j.datasets.iterator.impl.MnistDataSetIterator;
import org.kok202.deepblock.domain.stream.NumericRecordSet;
import org.kok202.deepblock.domain.stream.excel.ExcelWriter;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

import java.util.ArrayList;
import java.util.List;

public class MNISTDataLoader {

    public static void main(String[] args) throws Exception {
        List<INDArray> trainFeatures = new ArrayList<>();
        List<INDArray> trainLabels = new ArrayList<>();

        int offsetCounter = 0;
        int offsetStartFrom = 2048;
        int batchSize = 128;
        int batchNumber = 8;
        DataSetIterator iter = new MnistDataSetIterator(1,batchSize * batchNumber,false);
        while(iter.hasNext()){
            if(offsetCounter < offsetStartFrom) {
                offsetCounter++;
                continue;
            }
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

        NumericRecordSet numericFeatureRecordSet = new NumericRecordSet();
        for(int i = 0; i < trainFeatures.size(); i++){
            ArrayList<Double> featureRecord = new ArrayList<>();
            for(int j = 0; j < trainFeatures.get(0).length(); j++) {
                featureRecord.add(trainFeatures.get(i).getDouble(j));
            }
            numericFeatureRecordSet.addRecord(featureRecord);
        }
        ArrayList<String> headers = new ArrayList<>();
        for(int j = 0; j < trainFeatures.get(0).length(); j++) {
            headers.add("("+j/28+"px "+j%28+"px)");
        }
        numericFeatureRecordSet.setHeader(headers);
        trainFeatures = null;

        String filePath = "C:\\Users\\user\\Downloads\\mnist.xlsx";
        ExcelWriter excelWriter = new ExcelWriter(1, 1);
        excelWriter.writeAsXLSX(filePath, numericFeatureRecordSet);
        numericFeatureRecordSet = null;






        NumericRecordSet numericResultRecordSet = new NumericRecordSet();
        for(int i = 0; i < trainLabels.size(); i++){
            ArrayList<Double> resultRecord = new ArrayList<>();
            for(int j = 0; j < trainLabels.get(0).length(); j++)
                resultRecord.add(trainLabels.get(i).getDouble(j));
            numericResultRecordSet.addRecord(resultRecord);
        }
        headers = new ArrayList<>();
        for(int j = 0; j < trainLabels.get(0).length(); j++) {
            headers.add("(Result "+j+")");
        }
        numericResultRecordSet.setHeader(headers);
        trainLabels = null;
        filePath = "C:\\Users\\user\\Downloads\\mnist_label.xlsx";
        excelWriter = new ExcelWriter(1, 1);
        excelWriter.writeAsXLSX(filePath, numericResultRecordSet);

    }
}
