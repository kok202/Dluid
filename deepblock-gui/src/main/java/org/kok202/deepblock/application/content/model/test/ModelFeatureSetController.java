package org.kok202.deepblock.application.content.model.test;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import lombok.Data;
import org.kok202.deepblock.ai.interfaces.AIInterface;
import org.kok202.deepblock.application.adapter.NumericTableViewAdapter;
import org.kok202.deepblock.application.content.model.TabModelTestController;
import org.kok202.deepblock.domain.stream.NumericRecordSet;

@Data
public class ModelFeatureSetController extends AbstractModelTestController {

    @FXML private TableView tableViewDataSet;
    private NumericTableViewAdapter numericTableViewAdapter;

    public ModelFeatureSetController(TabModelTestController tabModelTestController) {
        super(tabModelTestController);
    }

    public AnchorPane createView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/model/test/featureset.fxml"));
        fxmlLoader.setController(this);
        AnchorPane content = fxmlLoader.load();
        return content;
    }

    @Override
    protected void initialize() throws Exception {
        numericTableViewAdapter = new NumericTableViewAdapter(tableViewDataSet);
    }

    public void refreshTableView(){
        NumericRecordSet testNumericRecordSet = AIInterface.getTestFeatureSet().getNumericRecordSet();
        numericTableViewAdapter.setRecordSetAndRefresh(testNumericRecordSet);
    }
}
