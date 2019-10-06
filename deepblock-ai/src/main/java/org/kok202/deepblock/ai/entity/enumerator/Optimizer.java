package org.kok202.deepblock.ai.entity.enumerator;

import lombok.Getter;

public enum Optimizer {
    ADAM("adam"),
    ADA_DELTA("adaDelta"),
    ADA_GRAD("adaGrad"),
    ADA_MAX("adaMax"),
    AMS_GRAD("amsGrad"),
    NADAM("nAdam"),
    NESTEROVS("nesterovs"),
    NOOP("noop"),
    RMS_PROP("rmsProp"),
    SGD("sgd"),;

    @Getter
    private String value;

    private Optimizer(String value) {
        this.value = value;
    }
}
