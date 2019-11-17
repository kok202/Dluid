package org.kok202.deepblock.domain.util;

import java.util.List;

public class ListUtil {
    public static double[] toDoubleArray(List<Double> list){
        double[] result = new double[list.size()];
        for (int i = 0; i < list.size(); i++)
            result[i] = list.get(i);
        return result;
    }

    public static String[] toStringArray(List<String> list){
        String[] result = new String[list.size()];
        for (int i = 0; i < list.size(); i++)
            result[i] = list.get(i);
        return result;
    }
}
