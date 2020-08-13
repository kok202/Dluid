package org.kokzoz.dluid.canvas.util;

import javafx.geometry.Point2D;
import org.kokzoz.dluid.canvas.CanvasConstant;
import org.kokzoz.dluid.domain.entity.Layer;

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
        return layer.getProperties().getInput().getX();
    }

    public static int getBlockNodeInputY(Layer layer){
        return layer.getProperties().getInput().getDimension() == 1
            ? 1
            : layer.getProperties().getInput().getY();
    }

    public static double getBlockNodeBottomX(Layer layer){
        return getBlockNodeOutputX(layer) * CanvasConstant.NODE_UNIT;
    }

    public static double getBlockNodeBottomY(Layer layer){
        return getBlockNodeOutputY(layer) * CanvasConstant.NODE_UNIT;
    }

    public static int getBlockNodeOutputX(Layer layer){
        return layer.getProperties().getOutput().getX();
    }

    public static int getBlockNodeOutputY(Layer layer){
        return layer.getProperties().getOutput().getDimension() > 1
            ? layer.getProperties().getOutput().getY()
            : 1;
    }

    public static double getBlockNodeZ(Layer layer){
        return layer.getProperties().getOutput().isHasChannel()
            ? layer.getProperties().getOutput().getChannel()
            : CanvasConstant.NODE_DEFAULT_HEIGHT;
    }
}
