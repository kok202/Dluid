package org.kok202.deepblock.ai.helper;

import lombok.Data;
import org.kok202.deepblock.domain.util.Counter;

@Data
public class RunnableTrainingTaskContainer {
    private Counter epochCounter;
    private Counter batchCounter;
    private double score;

    public RunnableTrainingTaskContainer(Counter epochCounter, Counter batchCounter, double score) {
        this.epochCounter = epochCounter;
        this.batchCounter = batchCounter;
        this.score = score;
    }
}
