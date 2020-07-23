package org.kok202.dluid.canvas.polygon.block;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

import static org.kok202.dluid.canvas.polygon.block.BlockFace.*;

public class Hexahedron {
    private HexahedronFace[] faces;
    private Map<BlockFace, String> textureSourceMap;
    private Map<BlockFace, Color> colorMap;

    private Hexahedron(Point3D leftTopFront, Point3D leftTopBack,
                       Point3D leftBottomFront, Point3D leftBottomBack,
                       Point3D rightTopFront, Point3D rightTopBack,
                       Point3D rightBottomFront, Point3D rightBottomBack) {
        this.faces = new HexahedronFace[BlockFace.size()];
        faces[FRONT.getIndex()] = new HexahedronFrontFace(
                leftTopFront,
                rightTopFront,
                leftBottomFront,
                rightBottomFront,
                this);
        faces[BACK.getIndex()] = new HexahedronBackFace(
                rightTopBack,
                leftTopBack,
                rightBottomBack,
                leftBottomBack,
                this);
        faces[LEFT.getIndex()] = new HexahedronLeftFace(
                leftTopBack,
                leftTopFront,
                leftBottomBack,
                leftBottomFront,
                this);
        faces[RIGHT.getIndex()] = new HexahedronRightFace(
                rightTopFront,
                rightTopBack,
                rightBottomFront,
                rightBottomBack,
                this);
        faces[TOP.getIndex()] = new HexahedronTopFace(
                leftTopBack,
                rightTopBack,
                leftTopFront,
                rightTopFront,
                this);
        faces[BOTTOM.getIndex()] = new HexahedronBottomFace(
                leftBottomFront,
                rightBottomFront,
                leftBottomBack,
                rightBottomBack,
                this);
    }

    public Hexahedron(Point3D leftTopFront, Point3D leftTopBack,
                      Point3D leftBottomFront, Point3D leftBottomBack,
                      Point3D rightTopFront, Point3D rightTopBack,
                      Point3D rightBottomFront, Point3D rightBottomBack,
                      Map<BlockFace, String> textureSourceMap,
                      Map<BlockFace, Color> colorMap) {
        this(leftTopFront, leftTopBack,
                leftBottomFront, leftBottomBack,
                rightTopFront, rightTopBack,
                rightBottomFront, rightBottomBack);
        this.textureSourceMap = (textureSourceMap != null)? textureSourceMap : new HashMap<>();
        this.colorMap = (colorMap != null)? colorMap : new HashMap<>();
        refreshBlockCover();
    }

    public void setTextureSourceMap(Map<BlockFace, String> textureSourceMap) {
        this.textureSourceMap = textureSourceMap;
    }

    public void setColorMap(Map<BlockFace, Color> colorMap) {
        this.colorMap = colorMap;
    }

    public void refreshBlockCover(){
        for(BlockFace blockFace : BlockFace.values()){
            // if texture exists, it has greater priority.
            String textureSource = textureSourceMap.get(blockFace);
            if(textureSource != null){
                faces[blockFace.getIndex()].setImage(textureSource);
            }
            else{
                // color is second thing.
                Color color = colorMap.get(blockFace);
                if(color != null){
                    faces[blockFace.getIndex()].setColor(color);
                }
            }
        }
    }

    public void setPosition(Point3D position){
        setPosition(position.getX(), position.getY(), position.getZ());
    }

    public void setPosition(double x, double y, double z){
        for(HexahedronFace hexahedronFace : faces){
            hexahedronFace.setTranslateX(x);
            hexahedronFace.setTranslateY(y);
            hexahedronFace.setTranslateZ(z);
        }
    }

    public void removedFromScene(Group sceneGroup){
        sceneGroup.getChildren().removeAll(faces);
    }

    public void addedToScene(Group sceneGroup){
        sceneGroup.getChildren().addAll(faces);
    }

    public void setVisible(boolean visible){
        for(HexahedronFace face : faces)
            face.setVisible(visible);
    }
}
