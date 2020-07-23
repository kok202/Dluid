package org.kok202.dluid.canvas.content;

import javafx.geometry.Point2D;
import lombok.Data;

@Data
public class BlockConnectionPayload {
    Point2D start;
    Point2D end;
    boolean visible;
}
