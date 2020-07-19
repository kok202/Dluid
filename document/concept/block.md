# Block

#### BlockNode
Conceptually block is same as blockNode, and consist of below data.
````
private BlockInfo blockInfo;
private Layer blockLayer;
private List<BlockHexahedron> blockHexahedronList;
````

#### BlockInfo
BlockInfo is structure of block node properties in for creating hexahedron model in canvas like position, node height, textures etc...
````
protected String id;
protected double height;
protected Point3D position;
protected List<String[]> textureSourcesList;
protected List<Color[]> colorsList;
protected Object extra;
````

#### BlockLayer
BlockLayer is layer type of block. 
You can get more information about layer data in [here](./layer.md).

#### BlockHexahedron
BlockNode is combination of some hexahedron models.
And BlockHexahedron is box polygon which has referencing to BlockNode. 
Basically blockNode has one hex. 
But when application need to show visually activation function is set. 
In that case we need to use two hex. so blockNode can have N numbers hexahedron.
````
private BlockNode blockNode;
````

