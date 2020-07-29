package org.kok202.dluid.canvas.content;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.PickResult;
import org.kok202.dluid.canvas.CanvasConstant;
import org.kok202.dluid.canvas.CanvasFacade;
import org.kok202.dluid.canvas.block.BlockNode;
import org.kok202.dluid.canvas.block.BlockNodeFactory;
import org.kok202.dluid.canvas.polygon.block.HexahedronBottomFace;
import org.kok202.dluid.canvas.polygon.block.HexahedronTopFace;
import org.kok202.dluid.canvas.polygon.block.HexahedronVerticalFace;
import org.kok202.dluid.canvas.reducer.MaterialInsertionDoneReducer;
import org.kok202.dluid.canvas.reducer.MaterialInsertionHoveringReducer;
import org.kok202.dluid.canvas.singleton.CanvasSingleton;
import org.kok202.dluid.canvas.util.Math3D;
import org.kok202.dluid.canvas.util.PickResultNodeUtil;
import org.kok202.dluid.domain.action.ActionType;
import org.kok202.dluid.domain.entity.Layer;
import org.kok202.dluid.domain.entity.LayerFactory;
import org.kok202.dluid.domain.entity.enumerator.LayerType;

// PickResult is always not null.
// Although user point at empty space, PickResult returns SubScene node
public class MaterialInsertionHandler {
    private Group sceneRoot;
    private HexahedronVerticalFace pastBlockHexahedronVerticalFace;

    public MaterialInsertionHandler(Group sceneRoot) {
        this.sceneRoot = sceneRoot;
        CanvasFacade.addReducer(new MaterialInsertionHoveringReducer(this));
        CanvasFacade.addReducer(new MaterialInsertionDoneReducer(this));
    }

    public void hoveringListener(MaterialInsertionInfoHolder materialInsertionInfoHolder){
        PickResult pickResult = materialInsertionInfoHolder.getDragEvent().getPickResult();
        Node pickResultNode = pickResult.getIntersectedNode();
        if(pastBlockHexahedronVerticalFace != pickResultNode){
            if(pastBlockHexahedronVerticalFace != null){
                pastBlockHexahedronVerticalFace.setColor(CanvasConstant.CONTEXT_COLOR_POSSIBLE_APPEND);
            }
        }
        if(pickResultNode instanceof HexahedronVerticalFace){
            HexahedronVerticalFace targetFace = (HexahedronVerticalFace) pickResultNode;
            BlockNode targetBlockNode = PickResultNodeUtil.convertToBlockNode(pickResult);
            if(pickResultNode instanceof HexahedronTopFace && targetBlockNode.isPossibleToAppendFrontByDirection()){
                targetFace.setColor(CanvasConstant.CONTEXT_COLOR_TRY_TO_APPEND);
                pastBlockHexahedronVerticalFace = targetFace;
            }
            else if(pickResultNode instanceof HexahedronBottomFace && targetBlockNode.isPossibleToAppendBackByDirection()){
                targetFace.setColor(CanvasConstant.CONTEXT_COLOR_TRY_TO_APPEND);
                pastBlockHexahedronVerticalFace = targetFace;
            }
        }
    }

    public void doneListener(MaterialInsertionInfoHolder materialInsertionInfoHolder){
        PickResult pickResult = materialInsertionInfoHolder.getDragEvent().getPickResult();
        Node pickResultNode = pickResult.getIntersectedNode();
        if(pickResultNode instanceof HexahedronVerticalFace){
            // Insert block by connecting.
            BlockNode targetBlockNode = PickResultNodeUtil.convertToBlockNode(pickResult);

            if(pickResultNode instanceof HexahedronTopFace && targetBlockNode.isPossibleToAppendFrontByDirection()){
                appendFrontToSpecificBlock(materialInsertionInfoHolder.getLayerType(), targetBlockNode);
            }
            else if(pickResultNode instanceof HexahedronBottomFace && targetBlockNode.isPossibleToAppendBackByDirection()){
                appendBackToSpecificBlock(materialInsertionInfoHolder.getLayerType(), targetBlockNode);
            }
        }
        else if(pickResultNode instanceof CoordinateGiantMesh){
            // Insert block on empty plane
            Point3D targetPoint = pickResult.getIntersectedPoint();
            Point3D cameraPoint = CanvasSingleton.getInstance().getMainCanvas().getMainScene().getCamera().getCurrentPosition();
            Point3D intersectedPoint = Math3D.getIntersectPoint(
                    new Point3D(0,0,1),
                    0,
                    targetPoint,
                    cameraPoint);
            createNewBlock(materialInsertionInfoHolder.getLayerType(), intersectedPoint);
        }
    }

    private void appendFrontToSpecificBlock(LayerType layerType, BlockNode targetBlockNode){
        Point3D selectedBlockPosition = targetBlockNode.getBlockInfo().getPosition();
        Point3D insertingPoint = selectedBlockPosition.add(new Point3D(0, -targetBlockNode.getBlockInfo().getHeight(), 0));

        // Add to global block node set and refresh component box material
        Layer layer = LayerFactory.create(layerType);
        BlockNode insertedBlockNode = insertLayerBlockModelToCanvas(layer, insertingPoint);
        CanvasSingleton.getInstance()
                .getBlockNodeManager()
                .linkFromNewData(insertedBlockNode, targetBlockNode);
        CanvasFacade.dispatchAction(ActionType.REFRESH_COMPONENT_LIST, insertedBlockNode.getBlockLayer());
    }

    private void appendBackToSpecificBlock(LayerType layerType, BlockNode targetBlockNode){
        Point3D selectedBlockPosition = targetBlockNode.getBlockInfo().getPosition();
        Point3D insertingPoint = selectedBlockPosition.add(new Point3D(0, targetBlockNode.getBlockInfo().getHeight(), 0));

        // Add to global block node set and refresh component box material
        Layer layer = LayerFactory.create(layerType);
        BlockNode insertedBlockNode = insertLayerBlockModelToCanvas(layer, insertingPoint);
        CanvasSingleton.getInstance()
                .getBlockNodeManager()
                .linkToNewData(targetBlockNode, insertedBlockNode);
        CanvasFacade.dispatchAction(ActionType.REFRESH_COMPONENT_LIST, insertedBlockNode.getBlockLayer());
    }

    private void createNewBlock(LayerType layerType, Point3D insertingPoint){
        // Add to global block node set and refresh component box material
        Layer layer = LayerFactory.create(layerType);
        BlockNode insertedBlockNode = insertLayerBlockModelToCanvas(layer, insertingPoint);
        CanvasSingleton.getInstance()
                .getBlockNodeManager()
                .registerSoloNode(insertedBlockNode);
        CanvasFacade.dispatchAction(ActionType.REFRESH_COMPONENT_LIST, insertedBlockNode.getBlockLayer());
    }

    private BlockNode insertLayerBlockModelToCanvas(Layer layer, Point3D insertingPoint){
        BlockNode blockNode = BlockNodeFactory.create(layer);
        blockNode.addedToScene(sceneRoot, insertingPoint);
        return blockNode;
    }
}
