package org.kok202.deepblock.canvas.util;

import javafx.scene.input.PickResult;
import org.kok202.deepblock.canvas.block.BlockNode;
import org.kok202.deepblock.canvas.polygon.block.BlockHexahedron;
import org.kok202.deepblock.canvas.polygon.block.HexahedronFace;

public class PickResultNodeUtil {
    public static HexahedronFace convertToBlockHexahedronFace(PickResult pickResult){
        return (HexahedronFace) pickResult.getIntersectedNode();
    }

    public static BlockNode convertToBlockNode(PickResult pickResult){
        HexahedronFace hexahedronFace = (HexahedronFace) pickResult.getIntersectedNode();
        BlockHexahedron blockHexahedron = (BlockHexahedron) hexahedronFace.getHexahedron();
        return blockHexahedron.getBlockNode();
    }

    public static boolean isBlockNode(PickResult pickResult){
        return  pickResult != null &&
                pickResult.getIntersectedNode() != null &&
                pickResult.getIntersectedNode() instanceof HexahedronFace;
    }
}
