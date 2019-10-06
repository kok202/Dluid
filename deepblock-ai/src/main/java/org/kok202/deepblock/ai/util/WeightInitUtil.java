package org.kok202.deepblock.ai.util;

import org.deeplearning4j.nn.weights.WeightInit;

import java.util.HashMap;

public class WeightInitUtil {
    private static HashMap<String, WeightInit> weightInitHashMap;

    private static HashMap<String, WeightInit> getWeightInitHashMap(){
        if(weightInitHashMap == null){
            weightInitHashMap = new HashMap<>();
            weightInitHashMap.put("distribution", WeightInit.DISTRIBUTION);
            weightInitHashMap.put("zero", WeightInit.ZERO);
            weightInitHashMap.put("ones", WeightInit.ONES);
            weightInitHashMap.put("sigmoidUniform", WeightInit.SIGMOID_UNIFORM);
            weightInitHashMap.put("normal", WeightInit.NORMAL);
            weightInitHashMap.put("lecunNormal", WeightInit.LECUN_NORMAL);
            weightInitHashMap.put("uniform", WeightInit.UNIFORM);
            weightInitHashMap.put("xavier", WeightInit.XAVIER);
            weightInitHashMap.put("xavierUniform", WeightInit.XAVIER_UNIFORM);
            weightInitHashMap.put("xavierFanIn", WeightInit.XAVIER_FAN_IN);
            weightInitHashMap.put("xavierLegacy", WeightInit.XAVIER_LEGACY);
            weightInitHashMap.put("relu", WeightInit.RELU);
            weightInitHashMap.put("reluUniform", WeightInit.RELU_UNIFORM);
            weightInitHashMap.put("identity", WeightInit.IDENTITY);
            weightInitHashMap.put("lecunUniform", WeightInit.LECUN_UNIFORM);
            weightInitHashMap.put("varScalingNormalFanIn", WeightInit.VAR_SCALING_NORMAL_FAN_IN);
            weightInitHashMap.put("varScalingNormalFanOut", WeightInit.VAR_SCALING_NORMAL_FAN_OUT);
            weightInitHashMap.put("varScalingNormalFanAvg", WeightInit.VAR_SCALING_NORMAL_FAN_AVG);
            weightInitHashMap.put("varScalingUniformFanIn", WeightInit.VAR_SCALING_UNIFORM_FAN_IN);
            weightInitHashMap.put("varScalingUniformFanOut", WeightInit.VAR_SCALING_UNIFORM_FAN_OUT);
            weightInitHashMap.put("varScalingUniformFanAvg", WeightInit.VAR_SCALING_UNIFORM_FAN_AVG);
        }
        return weightInitHashMap;
    }

    public static WeightInit convertToOptimizer(String weightInitString){
        return getWeightInitHashMap().get(weightInitString);
    }
}
