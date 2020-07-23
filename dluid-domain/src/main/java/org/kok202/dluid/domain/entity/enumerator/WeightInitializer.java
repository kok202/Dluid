package org.kok202.dluid.domain.entity.enumerator;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum WeightInitializer {
    ZERO,
    ONES,
    NORMAL,
    UNIFORM,
    XAVIER,

    /**********************************************************
     * Not real weight initializer. custom setting.
     **********************************************************/
    DISTRIBUTION_ZERO_TO_ONE,
    DISTRIBUTION_PLUS_MINUS_ONE,
    FOLLOW_GLOBAL_SETTING;
}