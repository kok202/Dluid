package org.kok202.dluid.ai.util;

import org.deeplearning4j.nn.conf.layers.PoolingType;
import org.kok202.dluid.domain.entity.enumerator.PoolingTypeWrapper;

public class PoolingTypeUtil {
    public static PoolingType get(PoolingTypeWrapper poolingTypeWrapper){
        switch (poolingTypeWrapper){
            case MAX:
                return PoolingType.MAX;
            case AVG:
                return PoolingType.AVG;
            case SUM:
                return PoolingType.SUM;
            case PNORM:
                return PoolingType.PNORM;
            default:
                return PoolingType.MAX;
        }
    }
}
