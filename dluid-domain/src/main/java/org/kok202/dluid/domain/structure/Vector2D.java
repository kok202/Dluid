package org.kok202.dluid.domain.structure;

import javafx.geometry.Point2D;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vector2D {
    private double x;
    private double y;

    public Vector2D(Point2D point2D) {
        x = point2D.getX();
        y = point2D.getY();
    }

    public Point2D convertToPoint(){
        return new Point2D(x, y);
    }
}
