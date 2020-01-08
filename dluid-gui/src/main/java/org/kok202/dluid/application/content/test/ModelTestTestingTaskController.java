package org.kok202.dluid.application.content.test;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lombok.Data;
import org.kok202.dluid.CanvasFacade;
import org.kok202.dluid.application.adapter.MenuAdapter;
import org.kok202.dluid.application.common.ExceptionHandler;
import org.kok202.dluid.application.content.TabModelTestController;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;

import java.util.List;
import java.util.stream.Collectors;

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
    }

    public void refreshTestTargetResultLayerInformation(String testInputLayerId){
        List<String> layerIds = CanvasFacade
                .findAllReachableNode(testInputLayerId)
                .stream()
                .filter(blockNodeGraphNode ->
                        !blockNodeGraphNode.getData().getBlockLayer().getType().isAssistLayerType() &&
                        !blockNodeGraphNode.getData().getBlockLayer().getType().isInputLayerType())
                .map(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getId())
                .collect(Collectors.toList());

        menuTestTargetResultLayerAdapter.clearMenuItems();
        layerIds.forEach(layerId -> menuTestTargetResultLayerAdapter.addMenuItem(layerId, layerId));
        menuTestTargetResultLayerAdapter.setDefaultMenuItemLast();
    }

    private void buttonTestActionHandler(){
        TestTask testTask = new TestTask();
        testTask.bindWithComponent(this);
        testTask.exceptionProperty().addListener((observable, oldValue, newValue) -> ExceptionHandler.catchException(Thread.currentThread(), newValue));
        Thread thread = new Thread(testTask);
        thread.setDaemon(true);
        thread.start();
    }
}
