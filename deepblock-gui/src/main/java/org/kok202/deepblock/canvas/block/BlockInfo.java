package org.kok202.deepblock.canvas.block;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import lombok.Data;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.domain.util.RandomUtil;

@Data
public class BlockInfo {
    protected long id;
    protected Layer layer;
    protected Point3D position;
    protected String[] layerTextureSources;
    protected String[] activationTextureSources;
    protected Color[] layerColors;
    protected Color[] activationColors;

    public BlockInfo(Layer layer) {
        this.id = RandomUtil.getLong();
        this.layer = layer;
        this.position = new Point3D(0,0,0);
    }

    public void setPosition(Point3D point3D){
        position = point3D;
    }

    public void setPosition(double x, double y, double z){
        position = new Point3D(x, y, z);
    }
}
