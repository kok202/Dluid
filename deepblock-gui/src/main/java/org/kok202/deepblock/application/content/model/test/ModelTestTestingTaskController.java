package org.kok202.deepblock.application.content.model.test;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lombok.Data;
import org.kok202.deepblock.application.content.model.TabModelTestController;

@Data
public class ModelTestTestingTaskController extends AbstractModelTestController {

    @FXML private TitledPane tiltedPane;
    @FXML private Label labelTestProgress;
    @FXML private TextArea textAreaTestLog;
    @FXML private ProgressBar progressBarTestProgress;
    @FXML private Button buttonTest;

    public ModelTestTestingTaskController(TabModelTestController tabModelTestController) {
        super(tabModelTestController);
    }

    public AnchorPane createView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/model/test/testing_task.fxml"));
        fxmlLoader.setController(this);
        AnchorPane content = fxmlLoader.load();
        return content;
    }

    @Override
    protected void initialize() throws Exception {
        buttonTest.setOnAction(event -> buttonTestActionHandler());
    }

    private void buttonTestActionHandler(){
        TestTask testTask = new TestTask();
        testTask.bindWithComponent(this);
        Thread thread = new Thread(testTask);
        thread.setDaemon(true);
        thread.start();
    }
}
