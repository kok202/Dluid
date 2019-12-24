package org.kok202.dluid.application.content.test;

import javafx.concurrent.Task;

public class TestTask extends Task<TestProgressContainer> {

    public void bindWithComponent(ModelTestTestingTaskController modelTestTestingTaskController) {
        valueProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue == null)
                return;
            if(newValue.isExistMessage())
                modelTestTestingTaskController.getTextAreaTestLog().appendText(newValue.getMessage() + "\n");
            if(newValue.isExistProgress())
                modelTestTestingTaskController.getProgressBarTestProgress().setProgress(newValue.getProgress());
        }));
    }

    @Override
    protected TestProgressContainer call() throws Exception {
        for (int i = 1; i <= 100; i++) {
            if (isCancelled()) {
                break;
            }
            updateProgress(i, 100);
            updateMessage(String.valueOf(i));
            //modelTestTestingTaskController.getTextAreaTestLog().appendText("progress : " + i + "\n");
        }
        return null;
    }

    @Override
    public void succeeded() {
    }
}