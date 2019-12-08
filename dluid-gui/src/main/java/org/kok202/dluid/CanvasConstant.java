package org.kok202.dluid;

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

    public static final Color COLOR_WHITE = Color.rgb(241, 242, 243);
    public static final Color COLOR_GRAY = Color.rgb(159, 159, 159);
    public static final Color COLOR_GRAY_ = Color.rgb(115, 115, 115);
    public static final Color COLOR_GRAY__ = Color.rgb(70, 70, 70);

    public static final Color COLOR_YELLOW = Color.rgb(255, 214, 146);

    public static final Color COLOR_RED = Color.rgb(232, 103, 87);
    public static final Color COLOR_RED_ = Color.rgb(167, 63, 63);
    public static final Color COLOR_RED__ = Color.rgb(117, 23, 43);

    public static final Color COLOR_PURPLE = Color.rgb(205, 111, 170);
    public static final Color COLOR_PURPLE_ = Color.rgb(169, 80, 130);
    public static final Color COLOR_PURPLE__ = Color.rgb(129, 50, 90);

    public static final Color COLOR_BLUE = Color.rgb(71, 184, 224);
    public static final Color COLOR_BLUE_ = Color.rgb(55, 158, 195);
    public static final Color COLOR_BLUE__ = Color.rgb(35, 138, 175);

    public static final Color CONTEXT_COLOR_POSSIBLE_APPEND = COLOR_YELLOW;
    public static final Color CONTEXT_COLOR_TRY_TO_APPEND = COLOR_RED;
    public static final Color CONTEXT_COLOR_IMPOSSIBLE_APPEND = COLOR_GRAY_;
    public static final Color CONTEXT_COLOR_BACKGROUND = COLOR_WHITE;
    public static final Color CONTEXT_COLOR_COORDINATE_AXIS = COLOR_GRAY__;

    public static final int CUBIC_CURVE_OFFSET_X = 1;
    public static final int CUBIC_CURVE_OFFSET_Y = 1;
    // For escaping pickResult always indicating cubic curve
    public static final Point2D CUBIC_CURVE_END_GAP_WHEN_UPWARD = new Point2D(2, 5);
    public static final Point2D CUBIC_CURVE_END_GAP_WHEN_DOWNWARD = new Point2D(2, -5);
}
