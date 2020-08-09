package org.kokzoz.dluid.content.test;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lombok.Data;
import org.kokzoz.dluid.AppFacade;
import org.kokzoz.dluid.adapter.MenuAdapter;
import org.kokzoz.dluid.canvas.CanvasFacade;
import org.kokzoz.dluid.common.ExceptionHandler;
import org.kokzoz.dluid.content.TabModelTestController;
import org.kokzoz.dluid.domain.action.ActionType;
import org.kokzoz.dluid.reducer.RefreshTestLogReducer;
import org.kokzoz.dluid.singleton.AppPropertiesSingleton;

import static org.kokzoz.dluid.domain.entity.enumerator.LayerType.RESHAPE_LAYER;

@Data
public class ModelTestTestingTaskController extends AbstractModelTestController {

    @FXML private TitledPane titledPane;
    @FXML private Label labelTestTargetResult;
    @FXML private MenuButton menuButtonTestTargetResultLayer;

    @FXML private TextArea textAreaTestLog;
    @FXML private ProgressBar progressBarTestProgress;
    @FXML private Button buttonTest;

    private MenuAdapter<String> menuTestTargetResultLayerAdapter;

    public ModelTestTestingTaskController(TabModelTestController tabModelTestController) {
        super(tabModelTestController);
    }

    public AnchorPane createView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/test/testing_task.fxml"));
        fxmlLoader.setController(this);
        AnchorPane content = fxmlLoader.load();
        return content;
    }

    @Override
    protected void initialize() throws Exception {
        menuTestTargetResultLayerAdapter = new MenuAdapter<>(menuButtonTestTargetResultLayer);
        buttonTest.setOnAction(event -> buttonTestActionHandler());
        labelTestTargetResult.setText(AppPropertiesSingleton.getInstance().get("frame.testTab.dataSetting.dataLoad.testTargetResultLayerId"));
        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.testTab.testTask.title"));
        buttonTest.setText(AppPropertiesSingleton.getInstance().get("frame.testTab.testTask.test"));
        AppFacade.addReducer(new RefreshTestLogReducer(this));
    }

    public void refreshTestTargetResultLayerInformation(String testInputLayerId){
        menuTestTargetResultLayerAdapter.clearMenuItems();
        CanvasFacade.findAllReachableNode(testInputLayerId).stream()
                .filter(blockNodeGraphNode ->
                        !blockNodeGraphNode.getData().getBlockLayer().getType().isAssistLayerType() &&
                                blockNodeGraphNode.getData().getBlockLayer().getType() != RESHAPE_LAYER &&
                                !blockNodeGraphNode.getData().getBlockLayer().getType().isInputLayerType())
                .map(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getId())
                .forEach(layerId -> menuTestTargetResultLayerAdapter.addMenuItem(layerId, layerId));
        menuTestTargetResultLayerAdapter.setDefaultMenuItemLast();
    }

    private void buttonTestActionHandler(){
        AppFacade.dispatchAction(ActionType.TEST_RESULT_CLEAR);
        TestTask testTask = new TestTask();
        testTask.bindWithComponent(this);
        testTask.exceptionProperty().addListener((observable, oldValue, newValue) -> {
            testTask.cancel();
            ExceptionHandler.catchException(Thread.currentThread(), newValue);
        });
        Thread thread = new Thread(testTask);
        thread.setDaemon(true);
        thread.start();
    }
}
