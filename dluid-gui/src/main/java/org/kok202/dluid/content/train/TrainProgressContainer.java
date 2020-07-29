package org.kok202.dluid.content.train;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class TrainProgressContainer {
    private String message;
    private double progress;
    private int epoch;
    private double score;

    public TrainProgressContainer() {
        message = null;
        progress = -1;
        epoch = -1;
        score = -1;
    }

    public TrainProgressContainer(String message) {
        this();
        this.message = message;
    }

    public TrainProgressContainer(double progress) {
        this();
        this.progress = progress;
    }

    public boolean isExistMessage() {
        return StringUtils.isNotEmpty(message);
    }

    public boolean isExistProgress() {
        return progress != -1;
    }

    public boolean isExistEpoch() {
        return epoch != -1;
    }

    public boolean isExistScore() {
        return score != -1;
    }
}