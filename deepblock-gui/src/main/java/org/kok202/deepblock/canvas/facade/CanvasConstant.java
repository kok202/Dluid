package org.kok202.deepblock.canvas.facade;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class CanvasConstant {
    public static final double NODE_UNIT = 0.1f;
    public static final double NODE_DEFAULT_HEIGHT = 0.5f;
    public static final double NODE_GAP = 0.4f;
    public static final double NODE_ACTIVATION_RATIO = 0.2f;
    public static final double CAMERA_DEPTH_MAX = -5;
    public static final double CAMERA_DEPTH_MIN = -50;
    public static final double CAMERA_FAR_CLIP = 1000;
    public static final double CAMERA_NEAR_CLIP = 0.1;
    public static final double CAMERA_MAX_ANGLE = 45;
    public static final int COORDINATE_MESH_SIZE = 100;
    public static final int COORDINATE_MESH_HEIGHT = 2;
    public static final int COORDINATE_SIZE = 100000;
    public static final int COORDINATE_DEPTH = 50;
    public static final double COORDINATE_MESH_BOLD_THICKNESS = 0.01;
    public static final double COORDINATE_MESH_NORM_THICKNESS = 0.003;

    public static final Color COLOR_LIGHT_GRAY = Color.rgb(128, 128, 128);
    public static final Color COLOR_GRAY = Color.rgb(89, 91, 93);
    public static final Color COLOR_DARK_GRAY = Color.rgb(60, 63, 65);
    public static final Color COLOR_WHITE = Color.rgb(255, 255, 255);
    public static final Color COLOR_YELLOW = Color.rgb(255, 214, 146);
    public static final Color COLOR_RED = Color.rgb(255, 99, 99);
    public static final Color COLOR_BLUE = Color.rgb(77, 210, 255);

    public static final Color CONTEXT_COLOR_POSSIBLE_APPEND = COLOR_YELLOW;
    public static final Color CONTEXT_COLOR_TRY_TO_APPEND = COLOR_RED;
    public static final Color CONTEXT_COLOR_IMPOSSIBLE_APPEND = COLOR_LIGHT_GRAY;
    public static final Color CONTEXT_COLOR_BACKGROUND = COLOR_DARK_GRAY;
    public static final Color CONTEXT_COLOR_COORDINATE_AXIS = COLOR_GRAY;

    public static final int CUBIC_CURVE_OFFSET_X = 1;
    public static final int CUBIC_CURVE_OFFSET_Y = 1;
    // For escaping pickResult always indicating cubic curve
    public static final Point2D CUBIC_CURVE_END_GAP_WHEN_UPWARD = new Point2D(2, 5);
    public static final Point2D CUBIC_CURVE_END_GAP_WHEN_DOWNWARD = new Point2D(2, -5);
}
