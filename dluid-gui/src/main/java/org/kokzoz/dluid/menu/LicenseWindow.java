package org.kokzoz.dluid.menu;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LicenseWindow {

    public static void show(){
        VBox vBox = new VBox();
        vBox.setStyle("-fx-padding:8;");
        for(String license : licenses){
            vBox.getChildren().add(new Label(license));
        }
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vBox);

        Scene newScene = new Scene(scrollPane, 800, 400);
        newScene.getStylesheets().add("css/common.css");

        Stage newStage = new Stage();
        newStage.setTitle("Licenses");
        newStage.setScene(newScene);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        newStage.setX((screenBounds.getWidth() - newScene.getWidth()) / 2);
        newStage.setY((screenBounds.getHeight() - newScene.getHeight()) / 2);
        newStage.show();
    }

    private static String[] licenses = new String[]{
            "openjfx 8",
            "http://hg.openjdk.java.net/openjfx/8u/rt",
            "GPL 2.0 License",
            "",
            "org.apache.commons.commons-math3 version 3.6.1",
            "https://mvnrepository.com/artifact/org.apache.commons/commons-math3",
            "Apache License 2.0",
            "",
            "org.projectlombok.lombok version 1.18.6",
            "https://mvnrepository.com/artifact/org.projectlombok/lombok",
            "MIT License",
            "",
            "com.fasterxml.jackson.core.jackson-core version 2.10.0",
            "https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core",
            "Apache License",
            "",
            "com.fasterxml.jackson.core.jackson-databind version 2.10.0",
            "https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind",
            "Apache License",
            "",
            "org.apache.poi.poi version 4.1.0",
            "https://mvnrepository.com/artifact/org.apache.poi/poi",
            "Apache License",
            "",
            "org.apache.poi.poi-ooxml version 4.1.0",
            "https://mvnrepository.com/artifact/org.apache.poi/poi-ooxml",
            "Apache License",
            "",
            "org.apache.commons.commons-compress version 1.19",
            "https://mvnrepository.com/artifact/org.apache.commons/commons-compress",
            "Apache License",
            "",
            "com.opencsv.opencsv version 4.6",
            "https://mvnrepository.com/artifact/com.opencsv/opencsv",
            "Apache License",
            "",
            "junit.junit version 4.11",
            "https://mvnrepository.com/artifact/junit/junit",
            "EPL license",
            "",
            "commons-io.commons-io version 2.5",
            "https://mvnrepository.com/artifact/commons-io/commons-io",
            "Apache License",
            "",
            "org.nd4j.nd4j-native-platform version 1.0.0-beta7",
            "https://mvnrepository.com/artifact/org.nd4j/nd4j-native-platform",
            "Apache License",
            "",
            "org.deeplearning4j.deeplearning4j-zoo version 1.0.0-beta7",
            "https://mvnrepository.com/artifact/org.deeplearning4j/deeplearning4j-zoo",
            "Apache License",
            "",
            "org.deeplearning4j.deeplearning4j-core version 1.0.0-beta7",
            "https://mvnrepository.com/artifact/org.deeplearning4j/deeplearning4j-core",
            "Apache License",
            "",
            "com.jfoenix.jfoenix version 9.0.6",
            "https://mvnrepository.com/artifact/com.jfoenix/jfoenix",
            "Apache License",
            "",
    };
}
