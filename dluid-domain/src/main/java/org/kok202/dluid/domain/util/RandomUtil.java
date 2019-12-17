package org.kok202.dluid.domain.util;

import java.util.Random;

public class RandomUtil {
    private static Random random;

    public static long getPositiveLong(){
        if(random == null)
            random = new Random(System.currentTimeMillis());
        return Math.abs(random.nextLong());
    }

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

    public static int getInt(int min, int max){
        return (int) getDouble(min, max);
    }

    public static int getInt(double min, double max){
        return (int) Math.round(getDouble(min, max));
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

    public static double getDouble(double min, double max){
        double randomRange = max - min;
        double randomDouble = getDouble() * randomRange;
        randomDouble += min;
        randomDouble = (randomDouble > max)? max : randomDouble;
        randomDouble = (randomDouble < min)? min : randomDouble;
        return randomDouble;
    }

    public static double getGaussian(){
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
