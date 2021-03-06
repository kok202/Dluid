package org.kokzoz.dluid.content.test;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import lombok.Data;
import org.kokzoz.dluid.AppFacade;
import org.kokzoz.dluid.adapter.MenuAdapter;
import org.kokzoz.dluid.adapter.NumericTableViewAdapter;
import org.kokzoz.dluid.ai.AIFacade;
import org.kokzoz.dluid.canvas.CanvasFacade;
import org.kokzoz.dluid.content.TabModelTestController;
import org.kokzoz.dluid.domain.action.ActionType;
import org.kokzoz.dluid.domain.stream.NumericRecordSet;
import org.kokzoz.dluid.reducer.RefreshTestInputLayerInformationReducer;
import org.kokzoz.dluid.singleton.AppPropertiesSingleton;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ModelTestFeatureTableController extends AbstractModelTestController {
    @FXML private TitledPane titledPane;
    @FXML private Label labelTestTarget;
    @FXML private MenuButton menuButtonTestTarget;
    @FXML private TableView tableViewDataSet;
    private MenuAdapter<String> menuTestTargetAdapter;
    private NumericTableViewAdapter numericTableViewAdapter;

    public ModelTestFeatureTableController(TabModelTestController tabModelTestController) {
        super(tabModelTestController);
    }

    public AnchorPane createView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/test/feature_table.fxml"));
        fxmlLoader.setController(this);
        AnchorPane content = fxmlLoader.load();
        return content;
    }

    @Override
    protected void initialize() throws Exception {
        menuTestTargetAdapter = new MenuAdapter<>(menuButtonTestTarget);
        menuTestTargetAdapter.setMenuItemChangedListener(testInputLayerId -> AppFacade.dispatchAction(ActionType.TEST_TARGET_RESULT_REFRESH, testInputLayerId));
        tableViewDataSet.setDisable(true);
        numericTableViewAdapter = NumericTableViewAdapter.builder()
                .tableView(tableViewDataSet)
                .editable(true)
                .build();
        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.testTab.dataSetting.dataTable.title"));
        labelTestTarget.setText(AppPropertiesSingleton.getInstance().get("frame.testTab.dataSetting.dataLoad.testTargetInputLayerId"));
        AppFacade.addReducer(new RefreshTestInputLayerInformationReducer(this));
    }

    public void refreshTestInputLayerInformation(){
        List<String> layerIds = CanvasFacade
                .findAllGraphNode(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getType().isInputLayerType())
                .stream()
                .map(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getId())
                .collect(Collectors.toList());

        menuTestTargetAdapter.clearMenuItems();
        layerIds.forEach(layerId -> menuTestTargetAdapter.addMenuItem(layerId, layerId));
        menuTestTargetAdapter.setDefaultMenuItem();
    }

    public void refreshTableView(){
        NumericRecordSet testNumericRecordSet = AIFacade.getTestFeatureSet().getNumericRecordSet();
        numericTableViewAdapter.setRecordSetAndRefresh(testNumericRecordSet);
        titledPane.setExpanded(true);
        tableViewDataSet.setDisable(false);
    }
}
