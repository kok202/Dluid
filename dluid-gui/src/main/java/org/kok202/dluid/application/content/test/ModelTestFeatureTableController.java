package org.kok202.dluid.application.content.test;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import lombok.Data;
import org.kok202.dluid.CanvasFacade;
import org.kok202.dluid.ai.AIFacade;
import org.kok202.dluid.application.adapter.MenuAdapter;
import org.kok202.dluid.application.adapter.NumericTableViewAdapter;
import org.kok202.dluid.application.content.TabModelTestController;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;
import org.kok202.dluid.application.singleton.AppWidgetSingleton;
import org.kok202.dluid.domain.stream.NumericRecordSet;

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
        menuTestTargetAdapter.setMenuItemChangedListener(testInputLayerId -> {
            AppWidgetSingleton.getInstance()
                    .getTabsController()
                    .getTabModelTestController()
                    .getModelTestTestingController()
                    .getModelTestTestingTaskController()
                    .refreshTestTargetResultLayerInformation(testInputLayerId);
        });
        numericTableViewAdapter = NumericTableViewAdapter.builder()
                .tableView(tableViewDataSet)
                .editable(true)
                .highlight(false)
                .build();
        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.testTab.dataSetting.dataTable.title"));
        labelTestTarget.setText(AppPropertiesSingleton.getInstance().get("frame.testTab.dataSetting.dataLoad.testTargetInputLayerId"));
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
    }
}
