package org.kok202.deepblock.canvas.content;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Translate;
import org.kok202.deepblock.canvas.singleton.CanvasConstant;

public class Coordinate extends Group {

    public Coordinate() {
        for(int i = -CanvasConstant.COORDINATE_MESH_SIZE; i < CanvasConstant.COORDINATE_MESH_SIZE; i++){
            double thickness = (i % 5 == 0)? CanvasConstant.COORDINATE_MESH_BOLD_THICKNESS : CanvasConstant.COORDINATE_MESH_NORM_THICKNESS;
            Box xAxis = createAxis(CanvasConstant.COORDINATE_MESH_SIZE, thickness, thickness);
            xAxis.getTransforms().add(new Translate(0, CanvasConstant.COORDINATE_MESH_HEIGHT, i));
            getChildren().add(xAxis);
        }
        for(int i = -CanvasConstant.COORDINATE_MESH_SIZE; i < CanvasConstant.COORDINATE_MESH_SIZE; i++){
            double thickness = (i % 5 == 0)? CanvasConstant.COORDINATE_MESH_BOLD_THICKNESS : CanvasConstant.COORDINATE_MESH_NORM_THICKNESS;
            Box zAxis = createAxis(thickness, thickness, CanvasConstant.COORDINATE_MESH_SIZE);
            zAxis.getTransforms().add(new Translate(i, CanvasConstant.COORDINATE_MESH_HEIGHT, 0));
            getChildren().add(zAxis);
        }

        getChildren().add(createGiantMesh(CanvasConstant.COORDINATE_SIZE, CanvasConstant.COORDINATE_DEPTH, CanvasConstant.COLOR_DARK_GRAY));
    }

    private Box createAxis(double x, double y, double z){
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(CanvasConstant.COLOR_GRAY);
        Box box = new Box(x,y,z);
        box.setMaterial(material);
        return box;
    }

    private CoordinateGiantMesh createGiantMesh(int size, int depth, Color color) {
        float coordinateHorizontal = size;
        float coordinateVertical = size;
        float coordinateDepth = depth;
        CoordinateGiantMesh coordinateGiantMesh = new CoordinateGiantMesh(
                new Point3D(-coordinateHorizontal, -coordinateVertical, coordinateDepth),
                new Point3D(coordinateHorizontal, -coordinateVertical, coordinateDepth),
                new Point3D(-coordinateHorizontal, coordinateVertical, coordinateDepth),
                new Point3D(coordinateHorizontal, coordinateVertical, coordinateDepth));
        coordinateGiantMesh.setColor(color);
        return coordinateGiantMesh;
    }
}