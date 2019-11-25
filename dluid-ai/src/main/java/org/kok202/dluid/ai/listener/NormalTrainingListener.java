package org.kok202.dluid.ai.listener;

import lombok.Builder;
import lombok.Getter;
import org.deeplearning4j.nn.api.Model;
import org.deeplearning4j.optimize.api.BaseTrainingListener;
import org.kok202.dluid.domain.util.Counter;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.List;

@Getter
public class NormalTrainingListener extends BaseTrainingListener {
    private Counter epochCounter;
    private Counter batchCounter;

    @Builder
    // totalRecordSize should follow binary square size (ex 1 2 4 8 16 32 64 ...)
    private NormalTrainingListener(int epochPrintPeriod, int epochSize, int batchPrintPeriod, int batchSize, int totalRecordSize) {
        this.epochCounter = new Counter();
        this.batchCounter = new Counter();
        int batchMax = (int) Math.ceil((double) (totalRecordSize) / batchSize);
        epochCounter.initialize(1, epochPrintPeriod, epochSize);
        batchCounter.initialize(1, batchPrintPeriod, batchMax);
    }

    // epoch start
    @Override
    public void onEpochStart(Model model) {
        batchCounter.reset();
        if(epochCounter.isAlarmIncludeStart()){
            System.out.println(String.format("epoch (%d) is started", epochCounter.getCount()));
        }
    }

    // batch : seq.1
    @Override
    public void onForwardPass(Model model, List<INDArray> activations) {
        if(batchCounter.isAlarmIncludeStart()){
            System.out.print("batch " + batchCounter.getCount() + " ( " + batchCounter.calcPercent() + " / 100% )");
            System.out.println(" : try to feedforward ");
        }
    }

    // batch : seq.2
    @Override
    public void onBackwardPass(Model model) {
        if(batchCounter.isAlarmIncludeStart()){
            System.out.print("batch " + batchCounter.getCount() + " ( " + batchCounter.calcPercent() + " / 100% )");
            System.out.println(" : try to backpropagation");
        }
    }

    // batch : seq.3
    @Override
    public void onGradientCalculation(Model model) {
        if(batchCounter.isAlarmIncludeStart()){
            System.out.print("batch " + batchCounter.getCount() + " ( " + batchCounter.calcPercent() + " / 100% )");
            System.out.println(" : try to calc gradient");
        }
    }

    // batch : seq.4
    @Override
    public void iterationDone(Model model, int iteration, int epoch) {
        if(batchCounter.isAlarmIncludeStart()){
            System.out.print("batch " + batchCounter.getCount() + " ( " + batchCounter.calcPercent() + " / 100% )");
            System.out.println(" : batch is done. and score is " + model.score());
        }
        batchCounter.count();
    }

    // epoch : end
    @Override
    public void onEpochEnd(Model model) {
        if(epochCounter.isAlarmIncludeStart()){
            System.out.println("epoch " + epochCounter.getCount() + " ( " + getTotalTrainingPercent() + " / 100% ) is ended");
        }
        epochCounter.count();
    }

    public double getTotalTrainingPercent(){
        double currentProgress = batchCounter.getCount() + (epochCounter.getCount()-1) * batchCounter.getMax();
        double totalProgress = epochCounter.getMax() * batchCounter.getMax();
        if(totalProgress == 0)
            return 0;
        int percent = (int)(currentProgress / totalProgress * 100);
        return Math.max(0, Math.min(percent, 100));
    }
}
