package org.kok202.deepblock.application.content.material.block;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.kok202.deepblock.ai.entity.enumerator.LayerType;
import org.kok202.deepblock.application.common.AbstractController;
import org.kok202.deepblock.domain.structure.Vector2D;

public abstract class AbstractMaterialController extends AbstractController {
    @Getter
    @Setter(AccessLevel.PROTECTED)
    protected LayerType layerType;

    private boolean isUseable = true;

    public Pane createView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/material/block/base_block.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    public boolean isUseable() {
        return isUseable;
    }

    protected void setStyleByBlockType(LayerType layerType){
        itself.getStyleClass().clear();
        itself.getStyleClass().add("layer-block-radius");
        switch(layerType){
            case DENSE_LAYER:
                itself.getStyleClass().add("layer-block-image-1d-fcnn");
                break;
            case CONVOLUTION_1D_LAYER:
                itself.getStyleClass().add("layer-block-image-1d-cnn");
                break;
            case CONVOLUTION_2D_LAYER:
                itself.getStyleClass().add("layer-block-image-2d-cnn");
                break;
            case DECONVOLUTION_2D_LAYER:
                itself.getStyleClass().add("layer-block-image-2d-decnn");
                break;
            case INPUT_LAYER:
                itself.getStyleClass().add("layer-block-image-input");
                break;
            case TRAIN_INPUT_LAYER:
                itself.getStyleClass().add("layer-block-image-train-input");
                break;
            case TEST_INPUT_LAYER:
                itself.getStyleClass().add("layer-block-image-test-input");
                break;
            case OUTPUT_LAYER:
                itself.getStyleClass().add("layer-block-image-output");
                break;
            case PIPE_LAYER:
                itself.getStyleClass().add("layer-block-image-pipe");
                break;
            case RESHAPE_LAYER:
                itself.getStyleClass().add("layer-block-image-reshape");
                break;
            default:
                itself.getStyleClass().add("layer-block-image-1d-fcnn");
                break;
        }
    }

    public void setOnDragDetected(EventHandler<MouseEvent> eventHandler){
        itself.setOnDragDetected(eventHandler);
    }

    public void relocatePosition(Vector2D mousePosition) {
        Point2D mouseCoords = new Point2D(mousePosition.getX(), mousePosition.getY());
        Point2D localCoords = itself.getParent().sceneToLocal(mouseCoords);
        int relocatedCoordsX = (int) (localCoords.getX() - (itself.getBoundsInLocal().getWidth() / 2));
        int relocatedCoordsY = (int) (localCoords.getY() - (itself.getBoundsInLocal().getHeight() / 2));
        itself.relocate(relocatedCoordsX, relocatedCoordsY);
    }
}
