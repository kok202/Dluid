package org.kokzoz.dluid.domain.exception;

import lombok.Getter;

@Getter
public class FeatureSetResultSetUnmatchedException extends RuntimeException {

    private String featureSetRowMax;
    private String resultSetRowMax;

    public FeatureSetResultSetUnmatchedException(long featureSetRowMax, int resultSetRowMax) {
        super("Record size of feature set and result set is different!");
        this.featureSetRowMax = String.valueOf(featureSetRowMax);
        this.resultSetRowMax = String.valueOf(resultSetRowMax);
    }

}
