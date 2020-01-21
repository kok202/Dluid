package org.kok202.dluid.application.content.design.component;

import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.entity.enumerator.ActivationWrapper;
import org.kok202.dluid.application.adapter.MenuAdapter;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;

public class ComponentRecurrentFunctionController extends ComponentCommonFunctionController {

    public ComponentRecurrentFunctionController(Layer layer) {
        super(layer);
    }

    @Override
    protected void initializeMenuButtonActivationFunction(){
        MenuAdapter<ActivationWrapper> menuAdapter = new MenuAdapter<>(getMenuButtonActivationFunction());
        menuAdapter.setMenuItemChangedListener(activation -> {
            layer.getProperties().setActivationFunction(activation);
            notifyLayerDataChanged();
        });
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.activationFunction.sigmoid"), ActivationWrapper.SIGMOID);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("deepLearning.activationFunction.softMax"), ActivationWrapper.SOFTMAX);
        menuAdapter.setDefaultMenuItem(layer.getProperties().getActivationFunction());
    }
}
