package org.kok202.dluid.canvas.block;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import lombok.Data;
import org.kok202.dluid.CanvasConstant;
import org.kok202.dluid.ai.entity.enumerator.LayerType;
import org.kok202.dluid.canvas.entity.InputBlockProperty;
import org.kok202.dluid.canvas.entity.MergeBlockProperty;
import org.kok202.dluid.canvas.entity.SkewedBlockProperty;
import org.kok202.dluid.domain.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

@Data
public class BlockInfo {
    protected long id;
    protected double height;
    protected Point3D position;
    protected List<String[]> textureSourcesList;
    protected List<Color[]> colorsList;
    protected Object extra;

    public BlockInfo(LayerType layerType) {
        this.id = RandomUtil.getLong();
        this.height = CanvasConstant.NODE_DEFAULT_HEIGHT;
        this.position = new Point3D(0,0,0);
        this.colorsList = new ArrayList<>();
        this.textureSourcesList = new ArrayList<>();

        switch (layerType){
            case INPUT_LAYER:
                extra = new InputBlockProperty();
                break;
            case MERGE_LAYER:
                extra = new MergeBlockProperty();
                break;
            default:
                extra = new SkewedBlockProperty();
                break;
        }
    }

    public void setPosition(Point3D point3D){
        position = point3D;
    }

    public void setPosition(double x, double y, double z){
        position = new Point3D(x, y, z);
    }
}
