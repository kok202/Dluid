package org.kok202.deepblock.domain.stream;

public class NumericConverter {
    public static int[] denormalize(int min, int max, double... values){
        int[] denormalizedValues = new int[values.length];
        int unit = max - min;
        for(int i = 0; i < values.length; i++){
            denormalizedValues[i] = (int)(values[i] * unit + min);
            denormalizedValues[i] = Math.min(max, denormalizedValues[i]);
            denormalizedValues[i] = Math.max(min, denormalizedValues[i]);
        }
        return denormalizedValues;
    }

    public static double[] normalize(int min, int max, int... values) {
        double[] normalizedValues = new double[values.length];
        double unit = max - min;
        for(int i = 0; i < values.length; i++){
            normalizedValues[i] = (values[i] - min) / unit;
            normalizedValues[i] = Math.min(1, normalizedValues[i]);
            normalizedValues[i] = Math.max(0, normalizedValues[i]);
        }
        return normalizedValues;
    }

    public static int convertAlphabetToInteger(String alphabets){
        int result = 0;
        int increment = 0;
        for(int i = alphabets.length() - 1; i >= 0; i--){
            char characterSingle = alphabets.charAt(i);
            int characterNum = characterSingle - 'A' + 1;
            int unit = (int) Math.pow(26, increment);
            result += characterNum * unit;
            increment++;
        }
        return result;
    }
}
