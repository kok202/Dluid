package org.kok202.dluid.canvas.block;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.kok202.dluid.CanvasConstant;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.canvas.polygon.block.BlockFace;
import org.kok202.dluid.canvas.polygon.block.BlockHexahedron;
import org.kok202.dluid.canvas.singleton.CanvasSingleton;

import java.util.ArrayList;
import java.util.List;

@ToString(exclude = {"blockHexahedronList"})
@EqualsAndHashCode(exclude = {"blockHexahedronList"})
// IMPORTANT : Because using hash set.
// Or stack overflow can be generated by bidirectional reference.
public abstract class BlockNode {

    private final int BLOCK_HEXAHEDRON_SIZE;

    @Getter
    private BlockInfo blockInfo;

    @Getter
    private Layer blockLayer;

    @Getter(AccessLevel.PROTECTED)
    private List<BlockHexahedron> blockHexahedronList;

    public BlockNode(Layer layer, int BLOCK_HEXAHEDRON_SIZE) {
        this.BLOCK_HEXAHEDRON_SIZE = BLOCK_HEXAHEDRON_SIZE;
        this.blockInfo = new BlockInfo(layer.getType(), BLOCK_HEXAHEDRON_SIZE);
        this.blockLayer = layer;
        clearHexahedronList();
        createBlockModel(layer);
    }

    protected void setBlockColor(int blockHexahedronIndex, BlockFace blockFace, Color color){
        getBlockInfo().getColorMapList().get(blockHexahedronIndex).put(blockFace, color);
    }

    protected void setBlockCover(int blockHexahedronIndex, BlockFace blockFace, String texture){
        getBlockInfo().getTextureSourceMapList().get(blockHexahedronIndex).put(blockFace, texture);
    }

    protected void refreshBlockCover(){
        for(int i = 0; i < BLOCK_HEXAHEDRON_SIZE; i++){
            BlockHexahedron blockHexahedron = blockHexahedronList.get(i);

            // Reload block color cover
            getBlockInfo().getColorMapList().get(i).put(BlockFace.TOP, (isPossibleToAppendFront())? CanvasConstant.CONTEXT_COLOR_POSSIBLE_APPEND : CanvasConstant.CONTEXT_COLOR_IMPOSSIBLE_APPEND);
            getBlockInfo().getColorMapList().get(i).put(BlockFace.BOTTOM, (isPossibleToAppendBack())? CanvasConstant.CONTEXT_COLOR_POSSIBLE_APPEND : CanvasConstant.CONTEXT_COLOR_IMPOSSIBLE_APPEND);
            blockHexahedron.setColorMap(getBlockInfo().getColorMapList().get(i));

            // Reload block texture cover
            blockHexahedron.setTextureSourceMap(getBlockInfo().getTextureSourceMapList().get(i));
            blockHexahedron.refreshBlockCover();
        }
    }

    public void translatePosition(double x, double y, double z){
        Point3D currentPosition = blockInfo.getPosition();
        Point3D targetPosition = currentPosition.add(x,y,z);
        setPosition(targetPosition.getX(), targetPosition.getY(), targetPosition.getZ());
    }

    public void addedToScene(Group sceneRoot, Point3D insertingPoint) {
        setPosition(insertingPoint.getX(), insertingPoint.getY(), 0);
        blockHexahedronList.forEach(blockHexahedron -> blockHexahedron.addedToScene(sceneRoot));
    }

    public final void deleteHexahedrons(){
        Group sceneRoot = CanvasSingleton.getInstance().getMainCanvas().getMainScene().getSceneRoot();
        blockHexahedronList.forEach(blockHexahedron -> {
            blockHexahedron.removedFromScene(sceneRoot);
        });
        clearHexahedronList();
    }

    private void clearHexahedronList(){
        if(blockHexahedronList == null){
            blockHexahedronList = new ArrayList<>();
        }
        blockHexahedronList.clear();
        for(int i = 0; i < BLOCK_HEXAHEDRON_SIZE; i++){
            blockHexahedronList.add(null);
        }
    }

    public abstract void setHeight(double height);

    public abstract void setPosition(double x, double y, double z);

    protected abstract void createBlockModel(Layer layer);

    public abstract void reshapeBlockModel();

    public abstract boolean isPossibleToAppendFront();

    public abstract boolean isPossibleToAppendBack();

    public abstract Point3D getTopCenterPosition();

    public abstract Point3D getBottomCenterPosition();
}
