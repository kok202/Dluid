package org.kok202.deepblock.domain.exception;

import org.kok202.deepblock.domain.structure.Vector2D;

public class DimensionUnmatchedException extends Exception {
    public DimensionUnmatchedException(int out, int in) {
        super("LayerType dimension is not matched : input size is " + in + ", but stream size is " + out);
    }

    public DimensionUnmatchedException(Vector2D out, Vector2D in) {
        super("LayerType dimension is not matched : input size is " + in.toString() + ", but stream size is " + out.toString());
    }

    public DimensionUnmatchedException(String out, String in) {
        super("LayerType dimension is not matched : input size is " + in + ", but stream size is " + out);
    }
}
