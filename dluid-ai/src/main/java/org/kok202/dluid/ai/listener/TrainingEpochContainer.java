package org.kok202.dluid.ai.listener;

import lombok.Builder;
import lombok.Data;
import org.kok202.dluid.domain.util.Counter;

@Data
public class TrainingEpochContainer {
    private Counter epochCounter;
    private Counter batchCounter;
    private double progress;
    private double score;

    @Builder
    public TrainingEpochContainer(Counter epochCounter, Counter batchCounter, double progress, double score) {
        this.epochCounter = epochCounter;
        this.batchCounter = batchCounter;
        this.progress = progress;
        this.score = score;
    }
}
