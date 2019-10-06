package org.kok202.deepblock.application.content.component;

import lombok.Getter;
import lombok.NonNull;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.application.content.ComponentContainerController;

import java.util.ArrayList;

public class ComponentManager {
    @Getter
    private ArrayList<AbstractComponentController> componentList;
    private ComponentContainerController componentContainerController;

    public ComponentManager(ComponentContainerController componentContainerController) {
        this.componentList = new ArrayList<>();
        this.componentContainerController = componentContainerController;
    }

    public void refreshContainerByLayer(@NonNull Layer layer){
        if(layer == null)
            return;

        clearComponentContainer();
        attachComponentBoxOnList(layer);
        attachComponentBoxOnContainer();
    }

    public void clearComponentContainer(){
        componentList.clear();
        componentContainerController.getComponentContainer().getChildren().clear();
    }

    private void attachComponentBoxOnList(Layer layer){
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
            case DECONVOLUTION_2D_LAYER:
                componentList.add(new ComponentCommonInfoController(layer));
                componentList.add(new ComponentCommonFunctionController(layer));
                componentList.add(new ComponentConvolution2DParamController(layer));
                break;
            case INPUT_LAYER:
                componentList.add(new ComponentCommonInfoController(layer));
                break;
            case OUTPUT_LAYER:
                componentList.add(new ComponentCommonInfoController(layer));
                componentList.add(new ComponentCommonFunctionController(layer));
                componentList.add(new ComponentOutputParamController(layer));
                break;
            case LSTM:
                componentList.add(new ComponentCommonInfoController(layer));
                componentList.add(new ComponentCommonFunctionController(layer));
                break;
        }
    }

    private void attachComponentBoxOnContainer(){
        try {
            for (AbstractComponentController abstractComponentController : componentList) {
                componentContainerController.getComponentContainer().getChildren().add(abstractComponentController.createView());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
