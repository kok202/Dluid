package org.kok202.deepblock.canvas.entity;

import javafx.geometry.Point3D;
import lombok.Data;

@Data
public class SkewedBlockProperty {
    private Point3D topSkewed; // NOTICE : Ignore y skewed
    private Point3D bottomSkewed; // NOTICE : Ignore y skewed
}
