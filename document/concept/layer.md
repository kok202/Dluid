---
title: Layer
parent: Concept
has_children: false
nav_order: 4
---

## Layer
````
protected final String id;
protected final LayerType type;
protected LayerProperties properties;
````
Dluid use `layer` for saving some parameter to dl4j layer. 
And it also has the type of itself.
Layer `id` is unique value in Dluid.
By this value, dluid can search layer.

### Layer properties
````
public class LayerProperties {
    // common properties
    private int[] inputSize;
    private int[] outputSize;
    private Dimension inputDimension;
    private Dimension outputDimension;
    private BiasInitializer biasInitializer;
    private WeightInitializer weightInitializer;
    private ActivationWrapper activationFunction;
    private double dropout;

    // for convolution type
    private int[] kernelSize;
    private int[] strideSize;
    private int[] paddingSize;

    // for output type
    private LossFunctionWrapper lossFunction;

    // for pooling layer
    private PoolingTypeWrapper poolingType;
}
````
Dluid save parameters for dl4j in one class without distinction.
That is `LayerProperties` class.

