package org.kokzoz.dluid.domain.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MathUtil {
    public static List<Integer> getDivisors(int size){
        if(size < 0)
            throw new RuntimeException("Size must be over than 0");
        if(size == 1)
            Collections.singletonList(1);

        List<Integer> divisors = new ArrayList<>();
        for(int i = 1; i <= size; i++){
            if(size % i == 0){
                divisors.add(i);
            }
        }

        Collections.sort(divisors);
        return divisors;
    }

}
