package org.kok202.deepblock.canvas.block;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import lombok.Data;
import org.kok202.deepblock.CanvasConstant;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.domain.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

@Data
public class BlockInfo {
    protected long id;
    protected double height;
    protected Layer layer;
    protected Point3D position;
    protected List<String[]> textureSourcesList;
    protected List<Color[]> colorsList;

    public BlockInfo(Layer layer) {
        this.id = RandomUtil.getLong();
        this.height = CanvasConstant.NODE_DEFAULT_HEIGHT;
        this.layer = layer;
        this.position = new Point3D(0,0,0);
        this.colorsList = new ArrayList<>();
        this.textureSourcesList = new ArrayList<>();
    }

    public void setPosition(Point3D point3D){
        position = point3D;
    }

    public void setPosition(double x, double y, double z){
        position = new Point3D(x, y, z);
    }
}
