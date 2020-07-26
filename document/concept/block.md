---
title: Block
parent: Concept
has_children: false
nav_order: 3
---

## Block
Conceptually block is same as BlockNode.
The reason why dluid calls block as `BlockNode` is because the name of `Block` seems to mean polygon form in openGL (cube like).
But block node can have several block polygon for visualizing blocks connections and flows.

### BlockNode
The simple structure of BlockNode is below.
````
public abstract class BlockNode {
    private BlockInfo blockInfo;
    private Layer blockLayer;
    private List<BlockHexahedron> blockHexahedronList;
    // ...
}
````
As you can see, Block bode is abstract class.
The block node has a hierarchy to better visualize the state and feature of block.
Brief information about large category is shown below.
- PlainBlockNode : A block node that does not need to contain visually additional information, such as an activation function.
- ActivationBlockNode : Block node which should visualize information about activation function.

### BlockInfo
BlockInfo is structure of block node properties in for creating hexahedron model in canvas like position, node height, textures etc...
````
protected String id;
protected double height;
protected Point3D position;
protected List<String[]> textureSourcesList;
protected List<Color[]> colorsList;
protected Object extra;
````

### BlockLayer
BlockLayer is layer type of block. 
You can get more information about layer data in [here](layer.md).

### BlockHexahedron
BlockNode is combination of some hexahedron models.
And BlockHexahedron is box polygon which has referencing to BlockNode. 
Basically blockNode has one hex. 
But when application need to show visually activation function is set. 
In that case we need to use two hex. so blockNode can have N numbers hexahedron.
````
public class BlockHexahedron extends Hexahedron {
    private BlockNode blockNode;
    // ...
}
````

