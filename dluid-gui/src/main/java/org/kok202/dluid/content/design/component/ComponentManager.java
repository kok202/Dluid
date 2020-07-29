package org.kok202.dluid.content.design.component;

import lombok.Getter;
import org.kok202.dluid.content.design.ComponentContainerController;
import org.kok202.dluid.domain.entity.Layer;

import java.util.ArrayList;

public class ComponentManager {
    @Getter
    private ArrayList<AbstractComponentController> componentList;
    private ComponentContainerController componentContainerController;

    public ComponentManager(ComponentContainerController componentContainerController) {
        this.componentList = new ArrayList<>();
        this.componentContainerController = componentContainerController;
    }

    public void clearComponentContainer(){
        componentList.clear();
        componentContainerController.getContainer().getChildren().clear();
    }

    public void attachComponentBoxOnList(Layer layer){
        switch (layer.getType()){
            case DENSE_LAYER:
                componentList.add(new ComponentCommonInfoController(layer));
                componentList.add(new ComponentCommonFunctionController(layer));
                componentList.add(new ComponentFeedforwardParamController(layer));
                break;
            case CONVOLUTION_1D_LAYER:
                componentList.add(new ComponentCommonInfoController(layer));
                componentList.add(new ComponentCommonFunctionController(layer));
                componentList.add(new ComponentConvolution1DParamController(layer));
                break;
            case CONVOLUTION_2D_LAYER:
                componentList.add(new ComponentCommonInfoController(layer));
                componentList.add(new ComponentCommonFunctionController(layer));
                componentList.add(new ComponentConvolution2DParamController(layer));
                break;
            case DECONVOLUTION_2D_LAYER:
                componentList.add(new ComponentCommonInfoController(layer));
                componentList.add(new ComponentCommonFunctionController(layer));
                componentList.add(new ComponentDeConvolution2DParamController(layer));
                break;
            case INPUT_LAYER:
                componentList.add(new ComponentCommonInfoController(layer));
                componentList.add(new ComponentInputParamController(layer));
                break;
            case OUTPUT_LAYER:
                componentList.add(new ComponentCommonInfoController(layer));
                componentList.add(new ComponentCommonFunctionController(layer));
                componentList.add(new ComponentOutputParamController(layer));
                break;
            case RNN_OUTPUT_LAYER:
                componentList.add(new ComponentCommonInfoController(layer));
                componentList.add(new ComponentCommonFunctionController(layer));
                componentList.add(new ComponentRecurrentOutputParamController(layer));
                break;
            case MERGE_LAYER:
                componentList.add(new ComponentCommonInfoController(layer));
                componentList.add(new ComponentMergeParamController(layer));
                break;
            case PIPE_LAYER:
                componentList.add(new ComponentCommonInfoController(layer));
                break;
            case RESHAPE_LAYER:
                componentList.add(new ComponentCommonInfoController(layer));
                componentList.add(new ComponentReshapeParamController(layer));
                break;
            case SWITCH_LAYER:
                componentList.add(new ComponentCommonInfoController(layer));
                componentList.add(new ComponentDelegateParamController(layer));
                break;
            case BASE_RECURRENT_LAYER:
            case LSTM:
                componentList.add(new ComponentCommonInfoController(layer));
                componentList.add(new ComponentCommonFunctionController(layer));
                componentList.add(new ComponentRecurrentParamController(layer));
                break;
            case POOLING_1D:
                componentList.add(new ComponentCommonInfoController(layer));
                componentList.add(new ComponentPooling1DParamController(layer));
                break;
            case POOLING_2D:
                componentList.add(new ComponentCommonInfoController(layer));
                componentList.add(new ComponentPooling2DParamController(layer));
                break;
            case BATCH_NORMALIZATION:
                componentList.add(new ComponentCommonInfoController(layer));
                componentList.add(new ComponentCommonFunctionController(layer));
                componentList.add(new ComponentDelegateParamController(layer));
                break;
        }
    }

    public void attachComponentBoxOnContainer(){
        try {
            for (AbstractComponentController abstractComponentController : componentList) {
                componentContainerController.getContainer().getChildren().add(abstractComponentController.createView());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
