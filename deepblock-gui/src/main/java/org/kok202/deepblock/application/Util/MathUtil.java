package org.kok202.deepblock.application.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MathUtil {
    public static List<Integer> getRecommendedDivisors(int size){
        if(size < 0)
            throw new RuntimeException("Size must be over than 0");
        if(size == 1)
            Collections.singletonList(1);

        List<Integer> divisors = new ArrayList<>();
        int sqrtSize = (int) Math.sqrt(size);
        for(int i = 1; i < sqrtSize; i++){
            if(size % i == 0){
                divisors.add(i);
                divisors.add(size/i);
            }
        }

        int middle = divisors.size() / 2;
        Collections.sort(divisors);
        divisors = divisors.subList(0, middle);
        return divisors;
    }
}
