package org.kok202.dluid.ai.singleton;

import lombok.Getter;
import org.kok202.dluid.ai.singleton.structure.DataSetManager;
import org.kok202.dluid.ai.singleton.structure.ModelManager;
import org.kok202.dluid.ai.singleton.structure.TrainDataManager;

@Getter
public class AISingleton {
    private static class GlobalDataHolder{
        private static final AISingleton instance = new AISingleton();
    }

    public static AISingleton getInstance(){
        return GlobalDataHolder.instance;
    }

    private ModelManager modelManager;
    private TrainDataManager trainDataManager;
    private DataSetManager testDataSetManager;

    private AISingleton(){
        modelManager = new ModelManager();
        trainDataManager = new TrainDataManager();
        testDataSetManager = new DataSetManager();
    }
}
