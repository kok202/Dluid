package org.kok202.dluid.ai.entity.enumerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.deeplearning4j.nn.weights.WeightInit;

@AllArgsConstructor
public enum WeightInitilaizer {
    ZERO(WeightInit.ZERO),
    ONES(WeightInit.ONES),
    NORMAL(WeightInit.NORMAL),
    UNIFORM(WeightInit.UNIFORM),
    XAVIER(WeightInit.XAVIER),

    /**********************************************************
     * Not real weight initializer. custom setting.
     **********************************************************/
    DISTRIBUTION_ZERO_TO_ONE(WeightInit.DISTRIBUTION),
    DISTRIBUTION_PLUS_MINUS_ONE(WeightInit.DISTRIBUTION),
    FOLLOW_GLOBAL_SETTING(null);

    @Getter
    private WeightInit weightInit;

}