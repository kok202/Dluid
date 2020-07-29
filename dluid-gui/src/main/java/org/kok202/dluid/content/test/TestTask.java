package org.kok202.dluid.content.test;

import javafx.concurrent.Task;
import org.kok202.dluid.AppFacade;
import org.kok202.dluid.ai.AIFacade;
import org.kok202.dluid.domain.action.ActionType;
import org.kok202.dluid.domain.stream.NumericRecordSet;
import org.kok202.dluid.model.ModelStateManager;

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
        AppFacade.dispatchAction(ActionType.TEST_ENABLE);
    }

    @Override
    protected TestProgressContainer call() throws Exception {
        updateValue(new TestProgressContainer("Check test possible."));
        ModelStateManager.validateTestPossible();
        updateValue(new TestProgressContainer("Check test possible. [Successful]"));
        updateValue(new TestProgressContainer("Test start."));
        NumericRecordSet resultNumericRecordSet = AIFacade.testModel(AppFacade.getTestInputLayerId(), AppFacade.getTestTargetResultLayerId());
        AIFacade.getTestResultSet().setNumericRecordSet(resultNumericRecordSet);
        return null;
    }

    @Override
    public void succeeded() {
        updateValue(new TestProgressContainer("Test done."));
        AppFacade.dispatchAction(ActionType.TEST_SUCCEED);
    }

    @Override
    public void cancelled() {
        AppFacade.dispatchAction(ActionType.TEST_ENABLE);
    }
}