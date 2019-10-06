package org.kok202.deepblock.application.content.model.test;

import javafx.concurrent.Task;

public class TestTask extends Task<Integer> {
    private ModelTestController modelTestController;

    public void bindWithComponent(ModelTestController modelTestController) {
        this.modelTestController = modelTestController;
        this.modelTestController.getProgressBarTestProgress().progressProperty().bind(this.progressProperty());
        this.modelTestController.getButtonTest().setDisable(true);
    }

    @Override
    protected Integer call() throws Exception {
        for (int i = 1; i <= 100; i++) {
            if (isCancelled()) {
                break;
            }
            updateProgress(i, 100);
            updateMessage(String.valueOf(i));
            //modelTestController.getTextAreaTestLog().appendText("progress : " + i + "\n");
        }
        return 100;
    }

    @Override
    public void succeeded() {
        // TODO : test 중인 로그를 볼수 있게 텍스트 필드가 필요하다.
        modelTestController.getTextAreaTestLog().appendText("Test done.\n");
        modelTestController.getButtonTest().setDisable(false);
    }

    private void stopTraining(){
        cancel();
    }
}