package com.example.charts;

import com.example.charts.files.ConfigReader;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class ConfigApplication extends Application {

    protected ConfigReader configReader;

    private long delay_ms;

    @Override
    public void start(Stage stage) {
        try {
            configReader = new ConfigReader();
        } catch (IOException e) {
            new ExceptionDialog(e).showAndWait();
            stage.close();
            return;
        }

        stage.setScene(createScene());
        stage.show();
        try {
            delay_ms = Long.parseUnsignedLong(configReader.get("Intervall"));
        }
        catch (IllegalArgumentException e) {
            delay_ms = 600000;
            new ExceptionDialog(e, "Set to value " + delay_ms).showAndWait();
        }
        endLoop();
    }

    private void endLoop()  {
        refresh();
        Task<Void> sleeper = new Task<>() {
            protected Void call(){
                try {
                    Thread.sleep(delay_ms);
                } catch (InterruptedException ignored) {

                }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> endLoop());
        new Thread(sleeper).start();
    }

    public static void main(String[] args) {
        launch(args);
    }

    protected abstract Scene createScene();
    protected abstract void refresh();
}
