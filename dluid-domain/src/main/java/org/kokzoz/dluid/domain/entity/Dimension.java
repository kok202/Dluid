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
        this.dimension = 0;
        if(x != 0) this.dimension++;
        if(y != 0) this.dimension++;
        if(z != 0) this.dimension++;
        if(channel != 0) this.hasChannel = true;
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
            return hasChannel? new int[]{x, channel} : new int[]{x};
        if(dimension == 2)
            return hasChannel? new int[]{x, y, channel} : new int[]{x, y};
        if(dimension == 3)
            return hasChannel? new int[]{x, y, z, channel} : new int[]{x, y, z};
        return null;
    }

    public void changeDimensionByType(DimensionType dimensionType){
        dimension = dimensionType.getDimension();
        hasChannel = dimensionType.isHasChannel();
    }

    public DimensionType getDimensionType(){
        if(dimension == 1)
            return hasChannel? DimensionType.ONE_DIMENSION_WITH_CHANNEL : DimensionType.ONE_DIMENSION;
        if(dimension == 2)
            return hasChannel? DimensionType.TWO_DIMENSION_WITH_CHANNEL : DimensionType.TWO_DIMENSION;
        return null;
    }

    public int getVolume(){
        int product = 1;
        if(x != 0 && dimension >= 1) product *= x;
        if(y != 0 && dimension >= 2) product *= y;
        if(z != 0 && dimension >= 3) product *= z;
        if(channel != 0 && hasChannel) product *= channel;
        return product;
    }
}
