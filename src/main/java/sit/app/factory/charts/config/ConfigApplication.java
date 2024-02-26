package sit.app.factory.charts.config;

import sit.app.factory.charts.files.ConfigReader;
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
        stage.setTitle(getTitle());
        stage.setFullScreen(true);
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

    /**
     * Geht in eine endlos-Schleife und ruft alle paar ms, welche in der Config zu definieren sind, {@link #refresh()} auf
     */
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

    /**
     * Verlangt die Erstellung einer Scene mit den jeweiligen Inhalten
     * @return Eine Szene mit den darzustellenden Inhalten
     */
    protected abstract Scene createScene();

    /**
     * Verlangt die Definition einer Methode, in welcher das Dargestellte aktualisiert wird
     */
    protected abstract void refresh();
    protected abstract String getTitle();
}
