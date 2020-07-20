## Module

Dluid follows layered architecture.
Only upper layer can reference down layer. 
The opposite direction must not be allowed.
```
dluid-gui -> dluid-ai     -> dluid-domain
          -> dluid-canvas -> dluid-domain
```

### dluid-gui
This module is responsible for the front end of the dluid. 
It is developed as javafx and draws application components. 
It then enters the model from the user and forwards it to the ai module for learning and testing.
Gui module interacts with user most closely.
And it is also responsible for receiving input from the user and forwarding it to the ai module.
So through this module user can make ai model train and test.

### dluid-canvas
Canvas module is a `canvas component` separated from the javafx.
In this module there is a codes related to openGL.
Since it is a lower module of the gui module, canvas module can not message to gui module directly. 
So this module using an `Observer pattern` to communicate with the gui module.
For more information of `observer`, see the topic [architecture - osserver pattern](./observer-pattern.md).

### dluid-ai
The ai module is the most important module of dluid.
It has been developed using dl4j.
Therefore, this module requires a lot of knowledge related to dl4j.
If you develop this module, you need to focus on the network package.
For more information, refer to the documents related to [layer binder](../concept/layer-binder.md) and [layer builder](../concept/layer-builder.md).

### dluid-domain
Domain module is a collection of entities used in the Dluid.
This module is lower module than ai module, so not dependent on `dl4j`.
Therefore, if domain module needs to handle class of dl4j, domain module make corresponding class in domain module like `~wrapper`.
And ai module implements util class that converts the `~wrapper class` to `dl4j class`.
And it has common components like exception and utils.

## Module interaction
The basic model `creation`, `training`, `test` process is below.
1. User interacts with the gui and canvas module, and create create GraphManager\<Layer\>.
1. The gui module forwards the GraphManager\<Layer\> to the ai module for model initialization.
1. The ai module analyzes GraphManager\<Layer\> and create a model based on ComputingGraph.
1. User commands train and test the model using by gui module.

