package org.kok202.deepblock.canvas.polygon.block;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import lombok.Data;

import static org.kok202.deepblock.canvas.polygon.block.BlockFace.*;

@Data
public class Hexahedron {
    private final int FACE_NUMBER = 6;
    private HexahedronFace[] faces;
    private String[] textureSources;
    private Color[] colors;

    private Hexahedron(Point3D leftTopFront, Point3D leftTopBack,
                       Point3D leftBottomFront, Point3D leftBottomBack,
                       Point3D rightTopFront, Point3D rightTopBack,
                       Point3D rightBottomFront, Point3D rightBottomBack) {
        this.faces = new HexahedronFace[FACE_NUMBER];
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
                      String[] textureSources,
                      Color[] colors) {
        this(leftTopFront, leftTopBack,
                leftBottomFront, leftBottomBack,
                rightTopFront, rightTopBack,
                rightBottomFront, rightBottomBack);
        if(textureSources != null)
            setTextureSources(textureSources);
        else if(colors != null)
            setColors(colors);
        refreshBlockCover();
    }

    public void refreshBlockCover(){
        if(textureSources != null){
            for(int i = 0; i < faces.length; i++)
                faces[i].setImage(textureSources[i]);
        }
        else if(colors != null){
            for(int i = 0; i < faces.length; i++)
                faces[i].setColor(colors[i]);
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
