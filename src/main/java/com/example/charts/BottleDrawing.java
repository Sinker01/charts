package com.example.charts;

import com.example.charts.files.MyFileReader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class BottleDrawing extends Application {

    private Bottle bottle = new Bottle();

    @Override
    public void start(Stage primaryStage) {
        MyFileReader.init_config("flasche:");
        // Create a Canvas

        endLoop();

        BorderPane pane  = new BorderPane(bottle);

        // Center the bottle node within the anchorPane
        //double centerX = (anchorPane.getPrefWidth() - bottle.getBoundsInLocal().getWidth()) / 2;
        //double centerY = (anchorPane.getPrefHeight() - bottle.getBoundsInLocal().getHeight()) / 2;

        Scene scene = new Scene(pane);

        // Set the stage title and scene
        primaryStage.setTitle("Bottle Drawing");
        primaryStage.setScene(scene);

        primaryStage.setFullScreen(true);

        // Show the stage
        primaryStage.show();
    }

    private void endLoop() {
        String[] result;
        try (MyFileReader reader = new MyFileReader(MyFileReader.pfad)) {
            result = reader.readLine().split(";");
        }
        catch (Exception e) {
            new ExceptionDialog(e);
            return;
        }

        int soll = Integer.parseInt(result[0]), ist = Integer.parseInt(result[1]);
        bottle.setSoll(soll);
        bottle.setIst(ist);

        HelloApplication.delay(MyFileReader.intervall, this::endLoop);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
