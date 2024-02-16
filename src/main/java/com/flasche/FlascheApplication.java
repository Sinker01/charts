package com.flasche;

import com.ConfigApplication;
import com.ExceptionDialog;
import com.files.MyFileReader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class FlascheApplication extends ConfigApplication {

    private final Bottle bottle = new Bottle();

    @Override
    protected Scene createScene() {
        BorderPane pane  = new BorderPane(bottle);
        return new Scene(pane);
    }

    @Override
    protected void refresh() {
        String[] result;
        try (MyFileReader reader = new MyFileReader(configReader.get("Flasche"))) {
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
        launch(args);
    }
}
