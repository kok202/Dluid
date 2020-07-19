package org.kok202.dluid.canvas.entity;

import javafx.geometry.Point3D;
import lombok.Data;

@Data
public class ExtraBlockProperty {
    private Point3D topSkewed; // NOTICE : Ignore y skewed
    private Point3D bottomSkewed; // NOTICE : Ignore y skewed

    public Point3D getTopSkewed() {
        return topSkewed;
    }

    public Point3D getBottomSkewed() {
        return bottomSkewed;
    }

    public void setTopSkewed(Point3D topSkewed) {
        this.topSkewed = new Point3D(topSkewed.getX(), 0, topSkewed.getZ());
    }

    public void setBottomSkewed(Point3D bottomSkewed) {
        this.bottomSkewed = new Point3D(bottomSkewed.getX(), 0, bottomSkewed.getZ());
    }
}
