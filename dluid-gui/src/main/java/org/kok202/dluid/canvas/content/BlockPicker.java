package org.kok202.dluid.canvas.content;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import org.kok202.dluid.AppFacade;
import org.kok202.dluid.canvas.block.BlockNode;
import org.kok202.dluid.canvas.polygon.block.HexahedronFace;
import org.kok202.dluid.canvas.util.PickResultNodeUtil;

public class BlockPicker {
    public static MouseEvent setOnMouseClicked(MouseEvent mouseEvent){
        PickResult pickResult = mouseEvent.getPickResult();
        if(pickResult != null && pickResult.getIntersectedNode() != null) {
            // You can find out selected node.
            if (pickResult.getIntersectedNode() instanceof HexahedronFace) {
                BlockNode blockNode = PickResultNodeUtil.convertToBlockNode(pickResult);
                AppFacade.refreshComponentContainer(blockNode.getBlockInfo().getLayer());
            }
        }
        return mouseEvent;
    }
}
