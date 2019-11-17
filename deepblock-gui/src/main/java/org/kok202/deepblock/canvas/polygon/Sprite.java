package org.kok202.deepblock.canvas.polygon;

import javafx.geometry.Point3D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.kok202.deepblock.CanvasConstant;

public class Sprite extends Quadrangle {

    public Sprite(
            Point3D leftTopFront, Point3D rightTopFront,
            Point3D leftBottomFront, Point3D rightBottomFront) {
        super(leftTopFront, rightTopFront, leftBottomFront, rightBottomFront);
        setColor(CanvasConstant.COLOR_WHITE);
    }

    public void setColor(Color diffuseColor){
        DefaultMaterial material = new DefaultMaterial();
        material.setDiffuseColor(diffuseColor);
        setMaterial(material);
    }

    public void setImage(String textureSource){
        Image texture = new Image(textureSource);
        DefaultMaterial material = new DefaultMaterial();
        material.setDiffuseMap(texture);
        setMaterial(material);
    }
}
