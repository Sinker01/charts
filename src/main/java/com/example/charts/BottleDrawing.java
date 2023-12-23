package com.example.charts;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class BottleDrawing extends Application {

    private Bottle bottle = new Bottle();

    @Override
    public void start(Stage primaryStage) throws Exception {
        FileReader.init_config("flasche:");
        // Create a Canvas


        bottle.setSoll(500);
        bottle.setIst(200);


        // Create a Scene
        Scene scene = new Scene(new BorderPane(bottle));

        // Set the stage title and scene
        primaryStage.setTitle("Bottle Drawing");
        primaryStage.setScene(scene);

        primaryStage.setFullScreen(true);

        // Show the stage
        primaryStage.show();
    }

    private void endLoop() throws IOException {
        FileReader reader = new FileReader(FileReader.pfad);
        String[] result = reader.readLine().split(";");

        bottle.setIst(Integer.parseInt(result[1]));
        bottle.setSoll(Integer.parseInt(result[0]));

        HelloApplication.delay(FileReader.intervall, ()->
                {
                    try {
                        endLoop();
                    }
                    catch (IOException e) {
                        //FehlerDialog auswerfen
                    }
                }
        );
    }

    public static void main(String[] args) {
        launch(args);
    }
}
