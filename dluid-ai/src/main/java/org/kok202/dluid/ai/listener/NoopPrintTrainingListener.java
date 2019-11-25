package org.kok202.dluid.ai.listener;

import org.deeplearning4j.nn.api.Model;
import org.deeplearning4j.optimize.api.BaseTrainingListener;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.List;
import java.util.Map;

public class NoopPrintTrainingListener extends BaseTrainingListener{

    // epoch start
    @Override
    public void onEpochStart(Model model) {
        System.out.println("onEpochStart");
    }

    // batch : seq.1
    @Override
    public void onForwardPass(Model model, List<INDArray> activations) {
        System.out.println("onForwardPass List");
    }

    // batch : seq.1
    @Override
    public void onForwardPass(Model model, Map<String, INDArray> activations) {
        System.out.println("onForwardPass Map");
    }

    // batch : seq.2
    @Override
    public void onBackwardPass(Model model) {
        System.out.println("onBackwardPass");
    }

    // batch : seq.3
    @Override
    public void onGradientCalculation(Model model) {
        System.out.println("onGradientCalculation");
    }

    // batch : seq.4
    @Override
    public void iterationDone(Model model, int iteration, int epoch) {
        System.out.println("iterationDone");
    }

    // epoch : end
    @Override
    public void onEpochEnd(Model model) {
        System.out.println("onEpochEnd");
    }
}
