package org.kok202.deepblock.canvas.polygon;

import javafx.geometry.Point3D;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

public class Quadrangle extends MeshView {
    public Quadrangle(
            Point3D leftTopFront, Point3D rightTopFront,
            Point3D leftBottomFront, Point3D rightBottomFront) {
        float[] meshPoints = {
                (float) leftTopFront.getX(), (float) leftTopFront.getY(), (float) leftTopFront.getZ(), // left top
                (float) rightTopFront.getX(), (float) rightTopFront.getY(), (float) rightTopFront.getZ(), // right top
                (float) leftBottomFront.getX(), (float) leftBottomFront.getY(), (float) leftBottomFront.getZ(), // left bottom
                (float) rightBottomFront.getX(), (float) rightBottomFront.getY(), (float) rightBottomFront.getZ() // right bottom
        };
        float[] textureCoords = {
                0, 0,
                1, 0,
                0, 1,
                1, 1
        };
        int[] faces = {
                0, 0, 3, 3, 1, 1, // triangle #1
                3, 3, 0, 0, 2, 2  // triangle #2
        };
        TriangleMesh mesh = new TriangleMesh();
        mesh.getPoints().setAll(meshPoints);
        mesh.getTexCoords().setAll(textureCoords);
        mesh.getFaces().setAll(faces);
        setMesh(mesh);
    }
}
