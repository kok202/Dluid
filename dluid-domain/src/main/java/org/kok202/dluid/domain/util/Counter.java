package org.kok202.dluid.domain.util;

import lombok.Getter;

@Getter
public class Counter {
    private int initValue;
    private int period;
    private int count;
    private int max;
    private static final double ROUNDED_UNIT = 1000d;

    public void initialize(int initValue, int period, int max) {
        this.initValue = initValue;
        this.period = period;
        this.max = max;
        reset();
    }

    public void count(){
        if(count >= max) {
            reset();
            return;
        }
        count++;
    }

    public boolean isAlarm(){
        return ((count % period) == 0);
    }

    public boolean isAlarmIncludeStart(){
        return isAlarm() || count == initValue;
    }

    public double calcPercent(){
        if(count == max)
            return 100;
        double result = count / (double)(max) * 100;
        return Math.round(result * ROUNDED_UNIT) / ROUNDED_UNIT;
    }

    public void reset() {
        count = initValue;
    }
}
