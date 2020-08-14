package org.kokzoz.dluid.domain.entity;

import lombok.Builder;
import lombok.Getter;
import org.kokzoz.dluid.domain.entity.enumerator.DimensionType;
import org.kokzoz.dluid.domain.exception.OutOfDimensionException;

@Getter
public class Dimension {
    private int x;
    private int y;
    private int z;
    private int channel;
    private DimensionType type;

    @Builder
    public Dimension(int x, int y, int z, int channel) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.channel = channel;
        int dimension = 0;
        boolean hasChannel = channel == 0;
        if(dimension == 1 && !hasChannel)
            type = DimensionType.ONE_DIMENSION;
        else if(dimension == 1 && hasChannel)
            type = DimensionType.ONE_DIMENSION_WITH_CHANNEL;
        else if(dimension == 2 && !hasChannel)
            type = DimensionType.TWO_DIMENSION;
        else if(dimension == 2 && hasChannel)
            type = DimensionType.TWO_DIMENSION_WITH_CHANNEL;
    }

    public boolean isHasChannel() {
        return type.isHasChannel();
    }

    public int getDimension() {
        return type.getDimension();
    }

    public void setX(int x) {
        if(type.getDimension() < 1)
            throw new OutOfDimensionException(1, "x");
        this.x = x;
    }

    public void setY(int y) {
        if(type.getDimension() < 2)
            throw new OutOfDimensionException(2, "y");
        this.y = y;
    }

    public void setZ(int z) {
        if(type.getDimension() < 3)
            throw new OutOfDimensionException(3, "z");
        this.z = z;
    }

    public void setChannel(int channel) {
        if(!type.isHasChannel())
            throw new OutOfDimensionException(-1, "channel");
        this.channel = channel;
    }

    public void setType(DimensionType type) {
        this.type = type;
    }

    public int[] asArray(){
        switch (type){
            case ONE_DIMENSION:
                return new int[]{x};
            case ONE_DIMENSION_WITH_CHANNEL:
                return new int[]{x, channel};
            case TWO_DIMENSION:
                return new int[]{x, y};
            case TWO_DIMENSION_WITH_CHANNEL:
                return new int[]{x, y, channel};
            default:
                return new int[]{};
        }
    }

    public int getVolume(){
        switch (type){
            case ONE_DIMENSION:
                return x;
            case ONE_DIMENSION_WITH_CHANNEL:
                return x * channel;
            case TWO_DIMENSION:
                return x * y;
            case TWO_DIMENSION_WITH_CHANNEL:
                return x * y * channel;
            default:
                return 1;
        }
    }
}
