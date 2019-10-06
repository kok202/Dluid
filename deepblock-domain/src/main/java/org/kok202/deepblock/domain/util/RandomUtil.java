package org.kok202.deepblock.domain.util;

import java.util.Random;

public class RandomUtil {
    private static Random random;

    public static long getLong(){
        if(random == null)
            random = new Random(System.currentTimeMillis());
        return random.nextLong();
    }

    public static int getInt(){
        if(random == null)
            random = new Random(System.currentTimeMillis());
        return random.nextInt();
    }

    public static int getInt(int bound){
        if(random == null)
            random = new Random(System.currentTimeMillis());
        return random.nextInt(bound);
    }

    public static double getFloat(){
        if(random == null)
            random = new Random(System.currentTimeMillis());
        return random.nextFloat();
    }

    public static double getDouble(){
        if(random == null)
            random = new Random(System.currentTimeMillis());
        return random.nextDouble();
    }

    public static double getGausian(){
        if(random == null)
            random = new Random(System.currentTimeMillis());
        return random.nextGaussian();
    }

    public static boolean getBoolean(){
        if(random == null)
            random = new Random(System.currentTimeMillis());
        return random.nextBoolean();
    }
}
