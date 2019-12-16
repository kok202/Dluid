package org.kok202.dluid.canvas.block;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import lombok.Data;
import org.kok202.dluid.CanvasConstant;
import org.kok202.dluid.ai.entity.enumerator.LayerType;
import org.kok202.dluid.canvas.entity.InputBlockProperty;
import org.kok202.dluid.canvas.entity.MergeBlockProperty;
import org.kok202.dluid.canvas.entity.ReshapeBlockProperty;
import org.kok202.dluid.canvas.entity.SkewedBlockProperty;
import org.kok202.dluid.canvas.polygon.block.BlockFace;
import org.kok202.dluid.domain.util.RandomUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class BlockInfo {
    protected long id;
    protected double height;
    protected Point3D position;
    protected List<Map<BlockFace, String>> textureSourceMapList;
    protected List<Map<BlockFace, Color>> colorMapList;
    protected Object extra;

    public BlockInfo(LayerType layerType, int blockHexahedronNumber) {
        this.id = RandomUtil.getLong();
        this.height = CanvasConstant.NODE_DEFAULT_HEIGHT;
        this.position = new Point3D(0,0,0);
        this.textureSourceMapList = new ArrayList<>();
        this.colorMapList = new ArrayList<>();

        for(int i = 0; i < blockHexahedronNumber; i++){
            this.textureSourceMapList.add(new HashMap<>());
            this.colorMapList.add(new HashMap<>());
        }

        switch (layerType){
            case INPUT_LAYER:
                extra = new InputBlockProperty();
                break;
            case MERGE_LAYER:
                extra = new MergeBlockProperty();
                break;
            case RESHAPE_LAYER:
                extra = new ReshapeBlockProperty();
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
