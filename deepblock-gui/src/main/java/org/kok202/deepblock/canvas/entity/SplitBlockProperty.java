package org.kok202.deepblock.canvas.entity;

import javafx.geometry.Point3D;
import lombok.Data;

@Data
public class SplitBlockProperty {
    private int[] splitLeftSize;
    private int[] splitRightSize;

    private Point3D leftTopSkewed; // NOTICE : Ignore y skewed
    private Point3D leftBottomSkewed; // NOTICE : Ignore y skewed

    private Point3D rightTopSkewed; // NOTICE : Ignore y skewed
    private Point3D rightBottomSkewed; // NOTICE : Ignore y skewed

    public SplitBlockProperty(){
        splitLeftSize = new int[]{5,1};
        splitRightSize = new int[]{5,1};

        leftTopSkewed = new Point3D(0,0,0);
        leftBottomSkewed = new Point3D(0,0,0);
        rightTopSkewed = new Point3D(0,0,0);
        rightBottomSkewed = new Point3D(0,0,0);
    }
}
