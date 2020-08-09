package org.kokzoz.dluid.canvas.block;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.kokzoz.dluid.canvas.CanvasConstant;
import org.kokzoz.dluid.canvas.entity.ExtraBlockProperty;
import org.kokzoz.dluid.canvas.entity.MergeBlockProperty;
import org.kokzoz.dluid.canvas.entity.ReshapeBlockProperty;
import org.kokzoz.dluid.canvas.polygon.block.BlockFace;
import org.kokzoz.dluid.domain.entity.Layer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(of = {"id"})
public class BlockInfo {
    protected String id;
    protected double height;
    protected Point3D position;
    protected List<Map<BlockFace, String>> textureSourceMapList;
    protected List<Map<BlockFace, Color>> colorMapList;
    protected ExtraBlockProperty extra;

    public BlockInfo(Layer layer, int blockHexahedronNumber) {
        this.id = "BLOCK : " + layer.getId();
        this.height = CanvasConstant.NODE_DEFAULT_HEIGHT;
        this.position = new Point3D(0,0,0);
        this.textureSourceMapList = new ArrayList<>();
        this.colorMapList = new ArrayList<>();

        for(int i = 0; i < blockHexahedronNumber; i++){
            this.textureSourceMapList.add(new HashMap<>());
            this.colorMapList.add(new HashMap<>());
        }

        switch (layer.getType()){
            case MERGE_LAYER:
                extra = new MergeBlockProperty();
                break;
            case RESHAPE_LAYER:
                extra = new ReshapeBlockProperty();
                break;
            default:
                extra = new ExtraBlockProperty();
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
