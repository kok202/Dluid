package org.kok202.dluid.application.content.test;

import javafx.concurrent.Task;

public class TestTask extends Task<Integer> {
    private ModelTestTestingTaskController modelTestTestingTaskController;

    public void bindWithComponent(ModelTestTestingTaskController modelTestTestingTaskController) {
        this.modelTestTestingTaskController = modelTestTestingTaskController;
        this.modelTestTestingTaskController.getProgressBarTestProgress().progressProperty().bind(this.progressProperty());
        this.modelTestTestingTaskController.getButtonTest().setDisable(true);
    }

    @Override
    protected Integer call() throws Exception {
        for (int i = 1; i <= 100; i++) {
            if (isCancelled()) {
                break;
            }
            updateProgress(i, 100);
            updateMessage(String.valueOf(i));
            //modelTestTestingTaskController.getTextAreaTestLog().appendText("progress : " + i + "\n");
        }
        return 100;
    }

    @Override
    public void succeeded() {
        // TODO : test 중인 로그를 볼수 있게 텍스트 필드가 필요하다.
        modelTestTestingTaskController.getTextAreaTestLog().appendText("Test done.\n");
        modelTestTestingTaskController.getButtonTest().setDisable(false);
    }

    private void stopTraining(){
        cancel();
    }
}