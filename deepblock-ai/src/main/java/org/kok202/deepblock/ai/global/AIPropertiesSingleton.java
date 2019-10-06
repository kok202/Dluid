package org.kok202.deepblock.ai.global;

import lombok.Data;
import org.kok202.deepblock.ai.global.structure.ModelInfoProperty;
import org.kok202.deepblock.ai.global.structure.ModelLayersProperty;
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

    private ModelLayersProperty modelLayersProperty;
    private ModelInfoProperty modelInfoProperty;
    private TrainProperty trainProperty;
    private TestProperty testProperty;

    private AIPropertiesSingleton(){
        modelLayersProperty = new ModelLayersProperty();
        modelInfoProperty = new ModelInfoProperty();
        trainProperty = new TrainProperty();
        testProperty = new TestProperty();
    }
}
