package org.kok202.deepblock.canvas.entity;

import javafx.geometry.Point2D;
import lombok.Data;

@Data
public class PipeBlockProperty {
    private Point2D topSkewed;
    private Point2D bottomSkewed;
    private double height;
}
