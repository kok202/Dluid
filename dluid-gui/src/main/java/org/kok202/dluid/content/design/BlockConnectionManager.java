package org.kok202.dluid.content.design;

import javafx.geometry.Point2D;
import lombok.Data;

@Data
public class BlockConnectionManager {
    private BlockConnectionFollower blockConnectionFollower;

    public BlockConnectionManager() throws Exception{
        blockConnectionFollower = new BlockConnectionFollower();
    }

    public void setVisible(boolean visible){
        blockConnectionFollower.setVisible(visible);
    }

    public void setStart(Point2D position) {
        blockConnectionFollower.setStart(position);
    }

    public void setEnd(Point2D position) {
        blockConnectionFollower.setEnd(position);
    }

    public boolean isUpward() {
        return (blockConnectionFollower.getStart().getY() - blockConnectionFollower.getEnd().getY()) > 0;
    }
}
