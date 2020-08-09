package org.kokzoz.dluid.ai.network.layer.applier;

import org.deeplearning4j.nn.conf.layers.Layer.Builder;
import org.deeplearning4j.nn.conf.layers.Pooling1D;
import org.deeplearning4j.nn.conf.layers.Pooling2D;
import org.deeplearning4j.nn.conf.layers.PoolingType;
import org.kokzoz.dluid.domain.entity.LayerProperties;

public class PoolingTypeApplier extends AbstractApplier {

    @Override
    public boolean support(Builder builder, LayerProperties layerProperties){
        return (builder instanceof Pooling1D.Builder ||
                builder instanceof Pooling2D.Builder) &&
                layerProperties.getPoolingType() != null;
    }

    @Override
    public void apply(Builder builder, LayerProperties layerProperties){
        if(builder instanceof Pooling1D.Builder){
            Pooling1D.Builder pooling1DBuilder = (Pooling1D.Builder) builder;
            pooling1DBuilder.setPoolingType(getPoolingType(layerProperties));
        }
        else if(builder instanceof Pooling2D.Builder){
            Pooling2D.Builder pooling2DBuilder = (Pooling2D.Builder) builder;
            pooling2DBuilder.setPoolingType(getPoolingType(layerProperties));
        }
    }

    private PoolingType getPoolingType(LayerProperties layerProperties){
        switch (layerProperties.getPoolingType()){
            case MAX:
                return PoolingType.MAX;
            case AVG:
                return PoolingType.AVG;
            case SUM:
                return PoolingType.SUM;
            case PNORM:
                return PoolingType.PNORM;
            default:
                return PoolingType.MAX;
        }
    }
}
