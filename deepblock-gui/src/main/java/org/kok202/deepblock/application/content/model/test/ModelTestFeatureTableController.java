package org.kok202.deepblock.application.content.model.test;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import lombok.Data;
import org.kok202.deepblock.ai.AIFacade;
import org.kok202.deepblock.application.adapter.NumericTableViewAdapter;
import org.kok202.deepblock.application.content.model.TabModelTestController;
import org.kok202.deepblock.domain.stream.NumericRecordSet;

@Data
public class ModelTestFeatureTableController extends AbstractModelTestController {
    @FXML private TitledPane titledPane;
    @FXML private TableView tableViewDataSet;
    private NumericTableViewAdapter numericTableViewAdapter;

    public ModelTestFeatureTableController(TabModelTestController tabModelTestController) {
        super(tabModelTestController);
    }

    public AnchorPane createView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/model/test/feature_table.fxml"));
        fxmlLoader.setController(this);
        AnchorPane content = fxmlLoader.load();
        return content;
    }

    @Override
    protected void initialize() throws Exception {
        numericTableViewAdapter = new NumericTableViewAdapter(tableViewDataSet);
    }

    public void refreshTableView(){
        NumericRecordSet testNumericRecordSet = AIFacade.getTestFeatureSet().getNumericRecordSet();
        numericTableViewAdapter.setRecordSetAndRefresh(testNumericRecordSet);
        titledPane.setExpanded(true);
    }
}
