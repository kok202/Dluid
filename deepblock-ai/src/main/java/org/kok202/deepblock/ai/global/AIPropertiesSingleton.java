package org.kok202.deepblock.ai.global;

import lombok.Data;
import org.kok202.deepblock.ai.global.structure.ModelProperty;
import org.kok202.deepblock.ai.global.structure.ModelSettingProperty;
import org.kok202.deepblock.ai.global.structure.TestProperty;
import org.kok202.deepblock.ai.global.structure.TrainProperty;

@Data
public class AIPropertiesSingleton {
    private static class GlobalDataHolder{
        private static final AIPropertiesSingleton instance = new AIPropertiesSingleton();
    }

    public static AIPropertiesSingleton getInstance(){
        return GlobalDataHolder.instance;
    }

    private ModelProperty modelProperty;
    private ModelSettingProperty modelSettingProperty;
    private TrainProperty trainProperty;
    private TestProperty testProperty;

    private AIPropertiesSingleton(){
        modelProperty = new ModelProperty();
        modelSettingProperty = new ModelSettingProperty();
        trainProperty = new TrainProperty();
        testProperty = new TestProperty();
    }
}
