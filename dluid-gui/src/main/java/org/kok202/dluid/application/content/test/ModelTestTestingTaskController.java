package org.kok202.dluid.application.content.test;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import lombok.Data;
import org.kok202.dluid.application.common.ExceptionHandler;
import org.kok202.dluid.application.content.TabModelTestController;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;

@Data
public class ModelTestTestingTaskController extends AbstractModelTestController {

    @FXML private TitledPane titledPane;
    @FXML private TextArea textAreaTestLog;
    @FXML private ProgressBar progressBarTestProgress;
    @FXML private Button buttonTest;

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
        buttonTest.setOnAction(event -> buttonTestActionHandler());
        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.testTab.testTask.title"));
        buttonTest.setText(AppPropertiesSingleton.getInstance().get("frame.testTab.testTask.test"));
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
