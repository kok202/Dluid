package org.kok202.dluid.application.content.design.material.insertion;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.SplitPane;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lombok.Data;
import org.kok202.dluid.canvas.CanvasFacade;
import org.kok202.dluid.canvas.content.MaterialInsertionInfoHolder;
import org.kok202.dluid.domain.entity.enumerator.LayerType;
import org.kok202.dluid.domain.exception.MultiInputLayerException;
import org.kok202.dluid.domain.exception.MultiOutputLayerException;

@Data
public class MaterialInsertionManager {
    private AnchorPane rootPane;
    private SplitPane mainSplitter;
    private AnchorPane materialContent;
    private Pane canvasContent;
    private MaterialInsertionFollower materialInsertionFollower;
    private boolean isBlockInsertionRequested;

    public void initialize() throws Exception{
        if(rootPane == null) throw new NullPointerException();
        if(mainSplitter == null) throw new NullPointerException();
        if(materialContent == null) throw new NullPointerException();
        if(canvasContent == null) throw new NullPointerException();
        initializeInvisibleFollower();
        initializeMouseEventListener();
    }

    private void initializeInvisibleFollower() throws Exception{
        isBlockInsertionRequested = false;
        materialInsertionFollower = new MaterialInsertionFollower();
        rootPane.getChildren().add(materialInsertionFollower.createView());
    }

    private void initializeMouseEventListener(){
        // seq 1: moving material.material follower
        mainSplitter.setOnDragOver((DragEvent dragEvent) -> {
            materialInsertionFollower.relocatePosition(new Point2D(dragEvent.getSceneX(), dragEvent.getSceneY()));
        });

        // seq 2: dragging on middle side
        canvasContent.setOnDragOver((DragEvent dragEvent) -> {
            if(isBlockInsertionRequested){
                // Set drop is available
                dragEvent.acceptTransferModes(TransferMode.ANY);

                // Send dragging event to canvas block handler
                MaterialInsertionInfoHolder materialInsertionInfoHolder = (MaterialInsertionInfoHolder) dragEvent.getDragboard().getContent(MaterialInsertionInfoHolder.DRAG_FOR_ADD_BLOCK);
                materialInsertionInfoHolder.setDragEvent(dragEvent);
                CanvasFacade.hoveringListener(materialInsertionInfoHolder);
            }
        });

        // seq 3: dragging is done on middle side
        canvasContent.setOnDragDropped((DragEvent dragEvent) -> {
            if(isBlockInsertionRequested){
                // get container from old clipboard, and replace new one
                MaterialInsertionInfoHolder materialInsertionInfoHolder = (MaterialInsertionInfoHolder) dragEvent.getDragboard().getContent(MaterialInsertionInfoHolder.DRAG_FOR_ADD_BLOCK);
                materialInsertionInfoHolder.setDragEvent(dragEvent);
                ClipboardContent content = new ClipboardContent();
                content.put(materialInsertionInfoHolder.DRAG_FOR_ADD_BLOCK, materialInsertionInfoHolder);
                dragEvent.getDragboard().setContent(content);
                dragEvent.setDropCompleted(true);
                CanvasFacade.insertListener(materialInsertionInfoHolder);
            }
        });

        // seq 4: When dragging is done
        rootPane.setOnDragDone ((DragEvent dragEvent) -> {
            isBlockInsertionRequested = false;
            materialInsertionFollower.setVisible(false);
        });
    }

    public EventHandler<MouseEvent> startBlockInsertion(LayerType layerType){
        // seq 0 : When user start clicking material.material for dragging
        return (MouseEvent event) -> {
            if(!checkIsPossibleToAddLayer(layerType))
                return;

            // build container and set on clipboard
            MaterialInsertionInfoHolder materialInsertionInfoHolder = new MaterialInsertionInfoHolder();
            materialInsertionInfoHolder.setLayerType(layerType);
            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.put(MaterialInsertionInfoHolder.DRAG_FOR_ADD_BLOCK, materialInsertionInfoHolder);
            materialInsertionFollower.setInsertionSetting(clipboardContent, layerType);
            materialInsertionFollower.relocatePosition(new Point2D(event.getSceneX(), event.getSceneY()));
            isBlockInsertionRequested = true;
        };
    }

    private boolean checkIsPossibleToAddLayer(LayerType layerType){
        if(layerType.isOutputLayerType()){
            if (CanvasFacade.findOutputLayer().isPresent())
                throw new MultiOutputLayerException();
        } else if(layerType.isInputLayerType()){
            if (!CanvasFacade.findAllInputLayer().isEmpty())
                throw new MultiInputLayerException();
        }
        return true;
    }
}
