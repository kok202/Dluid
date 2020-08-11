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
    private boolean hasChannel;
    private int dimension;

    @Builder
    public Dimension(int x, int y, int z, int channel) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.channel = channel;

        dimension = 0;
        if(x != 0) dimension++;
        if(y != 0) dimension++;
        if(z != 0) dimension++;
        if(channel != 0) dimension++;
    }

    public int getYOrChannel(){
        return hasChannel? y : channel;
    }

    public int getZOrChannel(){
        return hasChannel? z : channel;
    }

    public void setX(int x) {
        if(dimension < 1)
            throw new OutOfDimensionException(1, "x");
        this.x = x;
    }

    public void setY(int y) {
        if(dimension < 2)
            throw new OutOfDimensionException(2, "y");
        this.y = y;
    }

    public void setZ(int z) {
        if(dimension < 3)
            throw new OutOfDimensionException(3, "z");
        this.z = z;
    }

    public void setChannel(int channel) {
        this.channel = channel;
        this.hasChannel = true;
    }

    public int[] asArray(){
        if(dimension == 1)
            return new int[]{x};
        if(dimension == 2)
            return new int[]{x, getYOrChannel()};
        if(dimension == 3)
            return new int[]{x, y, getZOrChannel()};
        return null;
    }

    public void changeDimensionByType(DimensionType dimensionType){
        switch (dimensionType){
            case ONE_DIMENSION:
                dimension = 1;
                break;
            case TWO_DIMENSION:
            case TWO_DIMENSION_WITH_CHANNEL:
                dimension = 2;
                break;
            case THREE_DIMENSION:
            case THREE_DIMENSION_WITH_CHANNEL:
                dimension = 3;
                break;
        }
    }

    public DimensionType getDimensionType(){
        if(dimension == 1)
            return DimensionType.ONE_DIMENSION;
        if(dimension == 2)
            return hasChannel? DimensionType.TWO_DIMENSION_WITH_CHANNEL : DimensionType.TWO_DIMENSION;
        if(dimension == 3)
            return hasChannel? DimensionType.THREE_DIMENSION_WITH_CHANNEL : DimensionType.THREE_DIMENSION;
        return null;
    }

    public int getVolume(){
        int product = 1;
        if(x != 0) product *= x;
        if(y != 0) product *= x;
        if(z != 0) product *= x;
        if(channel != 0) product *= channel;
        return product;
    }
}
