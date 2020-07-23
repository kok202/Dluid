package org.kok202.dluid.canvas.util;

import javafx.geometry.Point2D;
import org.kok202.dluid.canvas.CanvasConstant;
import org.kok202.dluid.domain.entity.Layer;

public class BlockNodeUtil {

    public static Point2D getBlockNodeTopXY(Layer layer){
        return new Point2D(
                getBlockNodeTopX(layer),
                getBlockNodeTopY(layer));
    }

    public static Point2D getBlockNodeBottomXY(Layer layer){
        return new Point2D(
                getBlockNodeBottomX(layer),
                getBlockNodeBottomY(layer));
    }

    public static double getBlockNodeTopX(Layer layer){
        return getBlockNodeInputX(layer) * CanvasConstant.NODE_UNIT;
    }

    public static double getBlockNodeTopY(Layer layer){
        return getBlockNodeInputY(layer) * CanvasConstant.NODE_UNIT;
    }

    public static int getBlockNodeInputX(Layer layer){
        return layer.getProperties().getInputSizeX();
    }

    public static int getBlockNodeInputY(Layer layer){
        if(layer.getProperties().getInputDimension().getDimension() == 1)
            return 1;
        else if(layer.getProperties().getInputDimension().getDimension() == 2)
            return (layer.getProperties().getInputDimension().isHasChannel())? 1 : layer.getProperties().getInputSizeY();
        else if(layer.getProperties().getInputDimension().getDimension() == 3)
            return layer.getProperties().getInputSizeY();
        throw new RuntimeException("Can not find block node Y value");
    }

    public static double getBlockNodeBottomX(Layer layer){
        return getBlockNodeOutputX(layer) * CanvasConstant.NODE_UNIT;
    }

    public static double getBlockNodeBottomY(Layer layer){
        return getBlockNodeOutputY(layer) * CanvasConstant.NODE_UNIT;
    }

    public static int getBlockNodeOutputX(Layer layer){
        return layer.getProperties().getOutputSizeX();
    }

    public static int getBlockNodeOutputY(Layer layer){
        if(layer.getProperties().getOutputDimension().getDimension() == 1)
            return 1;
        else if(layer.getProperties().getOutputDimension().getDimension() == 2)
            return (layer.getProperties().getOutputDimension().isHasChannel())? 1 : layer.getProperties().getOutputSizeY();
        else if(layer.getProperties().getOutputDimension().getDimension() == 3)
            return layer.getProperties().getOutputSizeY();
        throw new RuntimeException("Can not find block node Y value");
    }

    public static double getBlockNodeZ(Layer layer){
        if(layer.getProperties().getInputDimension().getDimension() == 1)
            return CanvasConstant.NODE_DEFAULT_HEIGHT;
        else if(layer.getProperties().getInputDimension().getDimension() == 2)
            return (layer.getProperties().getInputDimension().isHasChannel())?
                    layer.getProperties().getInputSizeY() * CanvasConstant.NODE_DEFAULT_HEIGHT :
                    CanvasConstant.NODE_DEFAULT_HEIGHT;
        else if(layer.getProperties().getInputDimension().getDimension() == 3)
            return layer.getProperties().getInputSizeZ() * CanvasConstant.NODE_DEFAULT_HEIGHT;
        throw new RuntimeException("Can not find block node Z value");
    }
}
