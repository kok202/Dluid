package org.kok202.deepblock.ai.helper;

import lombok.Data;
import org.kok202.deepblock.domain.util.Counter;

@Data
public class RunnableTrainingTaskContainer {
    private Counter epochCounter;
    private Counter batchCounter;
    private double progress;
    private double score;

    public RunnableTrainingTaskContainer(Counter epochCounter, Counter batchCounter, double progress, double score) {
        this.epochCounter = epochCounter;
        this.batchCounter = batchCounter;
        this.progress = progress;
        this.score = score;
    }
}
