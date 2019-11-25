package org.kok202.dluid.application.content.material.insertion;

import javafx.event.EventHandler;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import lombok.Data;
import org.kok202.dluid.CanvasFacade;
import org.kok202.dluid.ai.entity.enumerator.LayerType;
import org.kok202.dluid.domain.structure.Vector2D;

@Data
public class MaterialInsertionManager {
    private AnchorPane rootPane;
    private SplitPane mainSplitter;
    private AnchorPane materialContent;
    private TabPane tabContent;
    private MaterialInsertionFollower materialInsertionFollower;
    private boolean isBlockInsertionRequested;

    public void initialize() throws Exception{
        if(rootPane == null) throw new NullPointerException();
        if(mainSplitter == null) throw new NullPointerException();
        if(materialContent == null) throw new NullPointerException();
        if(tabContent == null) throw new NullPointerException();
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
            materialInsertionFollower.relocatePosition(new Vector2D(dragEvent.getSceneX(), dragEvent.getSceneY()));
        });

        // seq 2: dragging on middle side
        tabContent.setOnDragOver((DragEvent dragEvent) -> {
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
        tabContent.setOnDragDropped((DragEvent dragEvent) -> {
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
            if(!CanvasFacade.isPossibleToAddLayerType(layerType))
                return;

            // create container and set on clipboard
            MaterialInsertionInfoHolder materialInsertionInfoHolder = new MaterialInsertionInfoHolder();
            materialInsertionInfoHolder.setLayerType(layerType);
            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.put(MaterialInsertionInfoHolder.DRAG_FOR_ADD_BLOCK, materialInsertionInfoHolder);
            materialInsertionFollower.setInsertionSetting(clipboardContent, layerType);
            materialInsertionFollower.relocatePosition(new Vector2D(event.getSceneX(), event.getSceneY()));
            isBlockInsertionRequested = true;
        };
    }
}
