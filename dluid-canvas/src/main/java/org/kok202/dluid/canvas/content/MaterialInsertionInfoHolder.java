package org.kok202.dluid.canvas.content;

import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import lombok.Data;
import org.kok202.dluid.domain.entity.enumerator.LayerType;

import java.io.Serializable;

@Data
public class MaterialInsertionInfoHolder implements Serializable {
    public static final DataFormat DRAG_FOR_ADD_BLOCK = new DataFormat("DRAG_FOR_ADD_BLOCK");

    private DragEvent dragEvent;
    private LayerType layerType;

    public MaterialInsertionInfoHolder() {
        dragEvent = null;
        layerType = null;
    }
}
