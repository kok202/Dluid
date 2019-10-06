package org.kok202.deepblock.canvas.polygon;

import javafx.geometry.Bounds;
import javafx.geometry.Point3D;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.shape.VertexFormat;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import lombok.Data;
import org.kok202.deepblock.canvas.singleton.CanvasConstant;

@Data
public class TextCreator {
    public static Group create(String text, Color color, float size, Point3D position, Point3D rotation) {
        Text textComponent = new Text(text);
        textComponent.setFont(CanvasConstant.BOLD_FONT_IN_CANVAS);
        Bounds bounds = textComponent.getLayoutBounds();

        Canvas canvas = new Canvas(bounds.getWidth(), bounds.getHeight());
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setTextAlign(TextAlignment.CENTER);
        context.setTextBaseline(VPos.CENTER);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        context.setFill(Color.WHITE);
        context.setFont(CanvasConstant.BOLD_FONT_IN_CANVAS);
        context.fillText(text, canvas.getWidth() * 0.5, canvas.getHeight() * 0.5);

        SnapshotParameters snapshotParameters = new SnapshotParameters();
        WritableImage writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
        WritableImage snapshot = canvas.snapshot(snapshotParameters, writableImage);
        PixelReader pixelReader = writableImage.getPixelReader();
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        for(int y = 0; y < writableImage.getHeight(); y++) {
            for(int x = 0; x < writableImage.getWidth(); x++) {
                Color pixelColor = pixelReader.getColor(x, y).grayscale();
                pixelWriter.setColor(x, y, color.deriveColor(0, 1, 1, pixelColor.getBrightness()));
            }
        }

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(snapshot);
        material.setSelfIlluminationMap(snapshot);
        material.setSpecularMap(snapshot);

        float halfOfWidth = (float) ((writableImage.getWidth() * 0.5) * (0.5 / writableImage.getHeight())) * size;
        float halfOfHeight = 0.5f * size;

        TriangleMesh mesh = new TriangleMesh(VertexFormat.POINT_TEXCOORD);
        mesh.getPoints().addAll(-halfOfWidth, -halfOfHeight, 0, halfOfWidth, -halfOfHeight, 0, -halfOfWidth, 0, 0, halfOfWidth, 0, 0);
        mesh.getTexCoords().addAll(0, 0, 1, 0, 0, 1, 1, 1);
        mesh.getFaces().addAll(2, 2, 1, 1, 0, 0, 2, 2, 3, 3, 1, 1);

        MeshView meshView = new MeshView(mesh);
        meshView.setMaterial(material);
        meshView.getTransforms().addAll(new Rotate(rotation.getX(), rotation.getY(),  rotation.getZ()));

        Group group = new Group();
        group.getChildren().add(meshView);
        group.getTransforms().add(new Translate(position.getX(), position.getY(), position.getZ()));
        return group;
    }
}
