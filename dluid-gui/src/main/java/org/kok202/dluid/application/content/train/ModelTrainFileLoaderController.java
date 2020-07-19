package org.kok202.dluid.application.content.train;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.kok202.dluid.ai.AIFacade;
import org.kok202.dluid.application.adapter.MenuAdapter;
import org.kok202.dluid.application.adapter.file.TrainFeatureFileFinder;
import org.kok202.dluid.application.adapter.file.TrainResultFileFinder;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;
import org.kok202.dluid.canvas.CanvasFacade;

import java.util.List;
import java.util.stream.Collectors;


public class ModelTrainFileLoaderController extends AbstractModelTrainController {

    @FXML private TitledPane titledPane;
    @FXML private Label labelTrainingTarget;
    @FXML private Label labelTrainingFeature;
    @FXML private Label labelTrainingResult;

    @FXML private MenuButton menuButtonTrainingTarget;
    @FXML private TextField textFieldFindTrainingFeature;
    @FXML private Button buttonFindTrainingFeature;
    @FXML private TextField textFieldFindTrainingResult;
    @FXML private Button buttonFindTrainingResult;

    private MenuAdapter<String> menuTrainingTargetAdapter;
    private TrainFeatureFileFinder trainFeatureFileFinder;
    private TrainResultFileFinder trainResultFileFinder;

    public AnchorPane createView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/train/file_loader.fxml"));
        fxmlLoader.setController(this);
        AnchorPane content = fxmlLoader.load();
        return content;
    }

    @Override
    protected void initialize() throws Exception {
        setTrainingTargetMenuButton();
        setButtonFeatureFinderActionHandler();
        setButtonResultFinderActionHandler();

        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.fileLoader.title"));
        labelTrainingTarget.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.fileLoader.trainTargetLayerId"));
        labelTrainingFeature.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.fileLoader.trainFeatureData"));
        labelTrainingResult.setText(AppPropertiesSingleton.getInstance().get("frame.trainTab.fileLoader.trainResultData"));
        refreshFileLoader();
    }

    private void setTrainingTargetMenuButton(){
        menuTrainingTargetAdapter = new MenuAdapter<>(menuButtonTrainingTarget);
        menuTrainingTargetAdapter.setMenuItemChangedListener(this::setTextField);
    }

    private void setButtonFeatureFinderActionHandler(){
        trainFeatureFileFinder = new TrainFeatureFileFinder(textFieldFindTrainingFeature, buttonFindTrainingFeature, menuButtonTrainingTarget);
        trainFeatureFileFinder.initialize();
    }

    private void setButtonResultFinderActionHandler(){
        trainResultFileFinder = new TrainResultFileFinder(textFieldFindTrainingResult, buttonFindTrainingResult, menuButtonTrainingTarget);
        trainResultFileFinder.initialize();
    }

    public void refreshFileLoader(){
        List<String> layerIds = CanvasFacade
                .findAllGraphNode(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getType().isInputLayerType())
                .stream()
                .map(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getId())
                .collect(Collectors.toList());

        menuTrainingTargetAdapter.clearMenuItems();
        layerIds.forEach(layerId -> menuTrainingTargetAdapter.addMenuItem(layerId, layerId));
        menuTrainingTargetAdapter.setDefaultMenuItem();

        setTitlePaneAvailable(!layerIds.isEmpty());
        AIFacade.remainFilterTrainDataManagerSet(layerIds);
    }

    public void setTextField(String layerId){
        trainFeatureFileFinder.setText(AIFacade.getTrainFeatureSet(layerId).getFilePath());
        trainResultFileFinder.setText(AIFacade.getTrainResultSet(layerId).getFilePath());
    }

    private void setTitlePaneAvailable(boolean available){
        titledPane.setExpanded(available);
        titledPane.setDisable(!available);
    }
}
