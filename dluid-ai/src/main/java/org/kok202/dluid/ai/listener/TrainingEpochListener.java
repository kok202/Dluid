package org.kok202.dluid.ai.listener;

import lombok.Builder;
import lombok.Getter;
import org.kok202.dluid.domain.util.Counter;

import java.util.function.Consumer;

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

    public void epochCount() {
        if(epochCounter.isAlarmIncludeStart()){
            if(epochTask != null) {
                epochTask.accept(TrainingEpochContainer
                        .builder()
                        .epochCounter(epochCounter)
                        .progress(epochCounter.calcPercent())
                        .build());
            }
        }
        epochCounter.count();
    }
}
