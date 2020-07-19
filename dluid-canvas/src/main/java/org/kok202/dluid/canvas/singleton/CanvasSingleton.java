package org.kok202.dluid.canvas.singleton;

import lombok.Data;
import lombok.NonNull;
import org.kok202.dluid.canvas.MainCanvas;
import org.kok202.dluid.canvas.singleton.structure.BlockNodeManager;

@Data
public class CanvasSingleton {
    private static class CanvasSingletonHolder {
        private static final CanvasSingleton instance = new CanvasSingleton();
    }

    public static CanvasSingleton getInstance(){
        return CanvasSingletonHolder.instance;
    }

    private MainCanvas mainCanvas;
    private BlockNodeManager blockNodeManager;
    private double cameraMovementSpeed = -0.01;
    private double cameraRotationSpeed = 0.02;
    private double cameraZoomSize = 0.05;

    public void setMainCanvas(@NonNull MainCanvas mainCanvas) {
        this.mainCanvas = mainCanvas;
    }

    public void afterAllWidgetSet(){
        this.mainCanvas.setResizingListener();
    }

    private CanvasSingleton(){
        blockNodeManager = new BlockNodeManager();
    }
}
