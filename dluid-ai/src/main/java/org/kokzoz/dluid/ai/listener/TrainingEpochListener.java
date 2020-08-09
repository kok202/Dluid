package org.kokzoz.dluid.ai.listener;

import lombok.Builder;
import lombok.Getter;
import org.kokzoz.dluid.domain.util.Counter;

import java.util.function.Consumer;
import java.util.function.DoubleSupplier;

@Getter
public class TrainingEpochListener {
    private Counter epochCounter;
    private Consumer epochTask;

    @Builder
    private TrainingEpochListener(int epochTaskPeriod, Consumer epochTask, int epochSize) {
        this.epochCounter = new Counter();
        this.epochTask = epochTask;
        epochCounter.initialize(1, epochTaskPeriod, epochSize);
    }

    public void count(DoubleSupplier doubleSupplier) {
        if(epochCounter.isAlarmIncludeStart()){
            if(epochTask != null) {
                epochTask.accept(TrainingEpochContainer
                        .builder()
                        .epochCounter(epochCounter)
                        .score(doubleSupplier.getAsDouble())
                        .progress(epochCounter.calcPercent())
                        .build());
            }
        }
        epochCounter.count();
    }
}
