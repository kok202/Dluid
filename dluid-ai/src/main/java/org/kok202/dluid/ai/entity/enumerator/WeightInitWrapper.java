package org.kok202.dluid.ai.entity.enumerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.deeplearning4j.nn.weights.WeightInit;

@AllArgsConstructor
public enum WeightInitWrapper {
    ZERO(WeightInit.ZERO),
    ONES(WeightInit.ONES),
    NORMAL(WeightInit.NORMAL),
    UNIFORM(WeightInit.UNIFORM),
    DISTRIBUTION_ZERO_TO_ONE(WeightInit.DISTRIBUTION),
    DISTRIBUTION_PLUS_MINUS_ONE(WeightInit.DISTRIBUTION),
    XAVIER(WeightInit.XAVIER);

    @Getter
    private WeightInit weightInit;

}