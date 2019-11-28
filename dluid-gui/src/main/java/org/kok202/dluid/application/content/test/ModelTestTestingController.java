package org.kok202.dluid.application.content.test;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Data;
import org.kok202.dluid.application.content.TabModelTestController;

@Data
public class ModelTestTestingController extends AbstractModelTestController {
    @FXML private Accordion accordion;
    @FXML private VBox container;

    private ModelTestTestingTaskController modelTestTestingTaskController;
    private ModelTestTestingResultTableController modelTestTestingResultTableController;

    public ModelTestTestingController(TabModelTestController tabModelTestController) {
        super(tabModelTestController);
    }

    public AnchorPane createView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/test/testing.fxml"));
        fxmlLoader.setController(this);
        AnchorPane content = fxmlLoader.load();
        return content;
    }

    @Override
    protected void initialize() throws Exception {
        modelTestTestingTaskController = new ModelTestTestingTaskController(getTabModelTestController());
        modelTestTestingResultTableController = new ModelTestTestingResultTableController(getTabModelTestController());

        modelTestTestingTaskController.createView();
        modelTestTestingResultTableController.createView();
        modelTestTestingResultTableController.getTitledPane().setExpanded(false);

        accordion.getPanes().add(modelTestTestingTaskController.getTitledPane());
        // accordion.setExpandedPane(modelTestTestingTaskController.getTitledPane()); // Not using looks more better
        container.getChildren().add(modelTestTestingResultTableController.getTitledPane());
    }
}