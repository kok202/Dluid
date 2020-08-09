package org.kokzoz.dluid.canvas.singleton;

import lombok.Data;
import lombok.NonNull;
import org.kokzoz.dluid.canvas.MainCanvas;
import org.kokzoz.dluid.canvas.reducer.RemoveAllGraphNodeReducer;
import org.kokzoz.dluid.canvas.reducer.RemoveGraphNodeReducer;
import org.kokzoz.dluid.canvas.reducer.ReshapeBlockReducer;
import org.kokzoz.dluid.canvas.singleton.structure.BlockNodeManager;
import org.kokzoz.dluid.domain.structure.StateMachine;

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
    private StateMachine stateMachine;
    private double cameraMovementSpeed = -0.01;
    private double cameraRotationSpeed = 0.02;
    private double cameraZoomSize = 0.05;

    public void setMainCanvas(@NonNull MainCanvas mainCanvas) {
        this.mainCanvas = mainCanvas;
    }

    private CanvasSingleton(){
        blockNodeManager = new BlockNodeManager();
        stateMachine = new StateMachine();
        stateMachine.addReducer(new RemoveGraphNodeReducer(blockNodeManager));
        stateMachine.addReducer(new RemoveAllGraphNodeReducer(blockNodeManager));
        stateMachine.addReducer(new ReshapeBlockReducer(blockNodeManager));
    }
}
