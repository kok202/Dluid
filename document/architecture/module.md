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

### dluid-canvas
opengl

### dluid-ai
dl4j.

### dluid-domain
The domain layer is usually a collection of common entities, but in fact, it is rare for gui module and ai module to have a shared entity. 
Because of this reasons, Dluid use domain layer as collection of exception and utils functions

