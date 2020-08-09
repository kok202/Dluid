package org.kokzoz.dluid.canvas.polygon.block;

import lombok.Getter;

public enum BlockFace {
    FRONT(0), BACK(1), LEFT(2), RIGHT(3), TOP(4), BOTTOM(5);

    @Getter
    private int index;

    public static int size(){
        return 6;
    }

    BlockFace(int index) {
        this.index = index;
    }
}
