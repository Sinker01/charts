package sit.app.factory.charts;

import sit.app.factory.charts.bottle.Bottle;
import sit.app.factory.charts.config.ConfigApplication;
import sit.app.factory.charts.config.ExceptionDialog;
import sit.app.factory.charts.files.IterableFileReader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class BottleApplication extends ConfigApplication {

    private final Bottle bottle = new Bottle();

    @Override
    protected Scene createScene() {
        BorderPane pane  = new BorderPane(bottle);
        return new Scene(pane);
    }

    @Override
    protected void refresh() {
        String[] result;
        try (IterableFileReader reader = new IterableFileReader(configReader.get("Flasche"))) {
            result = reader.readLine().split(";");
        }
        catch (Exception e) {
            new ExceptionDialog(e);
            return;
        }

        int soll = Integer.parseInt(result[0]), ist = Integer.parseInt(result[1]);
        bottle.setSoll(soll);
        bottle.setIst(ist);
    }

    @Override
    protected String getTitle() {
        return "Flasche";
    }

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> new ExceptionDialog(e).showAndWait());
        launch(args);
    }
}
