package org.kok202.dluid.application.content.train;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.kok202.dluid.CanvasFacade;
import org.kok202.dluid.ai.AIFacade;
import org.kok202.dluid.application.Util.TextFieldUtil;
import org.kok202.dluid.application.adapter.file.TrainFeatureFileFinder;
import org.kok202.dluid.application.adapter.file.TrainResultFileFinder;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;
import org.kok202.dluid.canvas.block.BlockNode;
import org.kok202.dluid.domain.structure.GraphNode;

import java.util.List;


public class ModelTrainFileLoaderController extends AbstractModelTrainController {

    @FXML private TitledPane titledPane;
    @FXML private Label labelTrainingTarget;
    @FXML private Label labelTrainingFeature;
    @FXML private Label labelTrainingResult;

    @FXML private TextField textFieldTrainingTarget;
    @FXML private TextField textFieldFindTrainingFeature;
    @FXML private Button buttonFindTrainingFeature;
    @FXML private TextField textFieldFindTrainingResult;
    @FXML private Button buttonFindTrainingResult;
    @FXML private Pagination paginationFileLoader;

    private TrainFeatureFileFinder trainFeatureFileFinder;
    private TrainResultFileFinder trainResultFileFinder;
    private List<GraphNode<BlockNode>> inputGraphNodes;

    public AnchorPane createView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/train/file_loader.fxml"));
        fxmlLoader.setController(this);
        AnchorPane content = fxmlLoader.load();
        return content;
    }

    @Override
    protected void initialize() throws Exception {
        TextFieldUtil.applyPositiveLongFilter(textFieldTrainingTarget, 0);
        setPaginationActionHandler();
        setButtonFeatureFinderActionHandler();
        setButtonResultFinderActionHandler();

        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.fileLoader.title"));
        labelTrainingTarget.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.fileLoader.trainTargetLayerId"));
        labelTrainingFeature.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.fileLoader.trainFeatureData"));
        labelTrainingResult.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.fileLoader.trainResultData"));
        refreshFileLoader();
    }

    private void setPaginationActionHandler(){
        paginationFileLoader.currentPageIndexProperty().addListener(event -> setTextField());
    }

    private void setButtonFeatureFinderActionHandler(){
        trainFeatureFileFinder = new TrainFeatureFileFinder(textFieldFindTrainingFeature, buttonFindTrainingFeature, textFieldTrainingTarget);
        trainFeatureFileFinder.initialize();
        // FIXME : Total count 변경
    }

    private void setButtonResultFinderActionHandler(){
        trainResultFileFinder = new TrainResultFileFinder(textFieldFindTrainingResult, buttonFindTrainingResult, textFieldTrainingTarget);
        trainResultFileFinder.initialize();
        // FIXME : Total count 변경
    }

    public void refreshFileLoader(){
        inputGraphNodes = CanvasFacade.findAllGraphNode(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getType().isTrainInputLayerType());
        paginationFileLoader.setPageCount(inputGraphNodes.size());
        paginationFileLoader.setCurrentPageIndex(0);
        setTextField();
        setTitlePaneAvailable(!inputGraphNodes.isEmpty());
    }

    private void setTitlePaneAvailable(boolean available){
        titledPane.setExpanded(available);
        titledPane.setDisable(!available);
    }

    private void setTextField(){
        if(paginationFileLoader.getCurrentPageIndex() >= inputGraphNodes.size())
            return;
        GraphNode<BlockNode> inputGraphNode = inputGraphNodes.get(paginationFileLoader.getCurrentPageIndex());
        long layerId = inputGraphNode.getData().getBlockLayer().getId();
        textFieldTrainingTarget.setText(String.valueOf(layerId));
        trainFeatureFileFinder.setText(AIFacade.getTrainFeatureSet(layerId).getFilePath());
        trainResultFileFinder.setText(AIFacade.getTrainResultSet(layerId).getFilePath());
    }
}
