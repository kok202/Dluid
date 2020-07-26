---
title: Layer binder & builder
parent: Concept
has_children: false
nav_order: 5
---

### Layer binder 
The main role of `LayerBinder` is connect layer to layer.
When creating AI model, some layers have different ways of connection.
So there are several layer binder.
And `LayerBinderManager` is collection of concrete class of layer binder.
`LayerBinderManager` can create dl4j model by using of them.
The goal of Layer binder manager is create `ComputationalGraph`.

The structure of LayerBinder is below.
```
public abstract class AbstractLayerBinder {
    public abstract boolean support(Layer layer);
    public abstract void bind(Layer layer, List<Layer> layerFroms, ComputationGraphConfiguration.GraphBuilder graphBuilder);
    // ...
}
```
By OOP polymorphism, layer binder show the type of dluid layer, and determine whether it should responsible for connection.
While it processing connection, binder should create real model (ComputationGraph). 
So it coworks with layer builder.

### Layer builder 
As the name builder, `LayerBuilder` is a class that produces layers.
It converts Dluid layer to dl4j layer.
And for creating dl4j layer, Dluid use template method pattern.

```
public abstract class AbstractLayerBuilder {
    public abstract boolean support(Layer layer);
    public org.deeplearning4j.nn.conf.layers.Layer build(Layer layer){
        Builder builder = createBuilder(layer);
        setCommonProperties(layer, builder);
        setAddOnProperties(layer, builder);
        return builder.build();
    }
    protected abstract Builder createBuilder(Layer layer);
    protected abstract void setCommonProperties(Layer layer, Builder builder);
    protected abstract void setAddOnProperties(Layer layer, Builder builder);
}
```
