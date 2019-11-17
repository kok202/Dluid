package org.kok202.deepblock.ai.listener;

import lombok.Builder;
import lombok.Getter;
import org.deeplearning4j.nn.api.Model;
import org.deeplearning4j.optimize.api.BaseTrainingListener;
import org.kok202.deepblock.domain.util.Counter;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.List;
import java.util.function.Consumer;

@Getter
public class RunnableTrainingListener extends BaseTrainingListener {
    private Counter epochCounter;
    private Counter batchCounter;
    private Consumer epochTask;
    private Consumer batchTask;

    @Builder
    // totalRecordSize should follow binary square size (ex 1 2 4 8 16 32 64 ...)
    private RunnableTrainingListener(int epochTaskPeriod, Consumer epochTask, int epochSize, int batchTaskPeriod, Consumer batchTask, int batchSize, int totalRecordSize) {
        this.epochCounter = new Counter();
        this.batchCounter = new Counter();
        this.epochTask = epochTask;
        this.batchTask = batchTask;
        int batchMax = (int) Math.ceil((double) (totalRecordSize) / batchSize);
        epochCounter.initialize(1, epochTaskPeriod, epochSize);
        batchCounter.initialize(1, batchTaskPeriod, batchMax);
    }

    // epoch start
    @Override
    public void onEpochStart(Model model) {
        batchCounter.reset();
    }

    // batch : seq.1
    @Override
    public void onForwardPass(Model model, List<INDArray> activations) {
    }

    // batch : seq.2
    @Override
    public void onBackwardPass(Model model) {
    }

    // batch : seq.3
    @Override
    public void onGradientCalculation(Model model) {
    }

    // batch : seq.4
    @Override
    public void iterationDone(Model model, int iteration, int epoch) {
        if(batchCounter.isAlarmIncludeStart()){
            if(batchTask != null) {
                double totalProgress = getTotalTrainingPercent();
                batchTask.accept(new RunnableTrainingTaskContainer(epochCounter, batchCounter, totalProgress, model.score()));
            }
        }
        batchCounter.count();
    }

    // epoch : end
    @Override
    public void onEpochEnd(Model model) {
        if(epochCounter.isAlarmIncludeStart()){
            if(epochTask != null) {
                double totalProgress = getTotalTrainingPercent();
                epochTask.accept(new RunnableTrainingTaskContainer(epochCounter, batchCounter, totalProgress, model.score()));
            }
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
