package com.example.charts;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.RunnableScheduledFuture;

public class HelloApplication extends Application {

    public String pfad, start, ende;
    public int delay, intervall;
    private LineChart<String, Number> chart;

    @Override
    public void start(Stage stage) throws IOException {
            init_config();
            //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            // Scene scene = new Scene(fxmlLoader.load(), 320, 240);
            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();
            chart = new LineChart<>(xAxis, yAxis);

            //xAxis.setAutoRanging(false);
            xAxis.setCategories(FXCollections.observableArrayList("06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18,00", "19:00"));

            BorderPane pane = new BorderPane();
            pane.setCenter(chart);

            Scene scene = new Scene(pane);

            stage.setTitle("Hello!");
            stage.setScene(scene);
            //stage.setFullScreen(true);
            stage.setMaximized(true);
            stage.show();
            endLoop();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void init_config() throws IOException {
        FileReader rd = new FileReader("files/config.ini");
        int i = 0;
        for(String line = rd.readLine(); line != null; line = rd.readLine()) {
            if(line.isEmpty()) continue;
            i++;
            String[] split = line.split(" ");
            if(split.length!=2) throw new IOException("Linie " + i + " mit dem Inhalt " + line + " im config.ini falsch formatiert.");
            switch (split[0]) {
                case "Verz:":
                    pfad = split[1];
                    break;
                case "Start:":
                    start=split[1];
                    break;
                case "Ende:":
                    ende = split[1];
                    break;
                case "Intervall:":
                    intervall=Integer.parseInt(split[1]);
                    break;
                case "Delay:":
                    delay = Integer.parseInt(split[1]);
                    break;
                default:
                    throw new IOException("Linie " + i + " mit dem Inhalt " + line + " im config.ini falsch formatiert.");
            }
        }
        if(i!=5 ) throw new IOException("Zu wenig Argumente in config.ini");
    }

    private int z = 0;
    private void endLoop() throws IOException{
        System.out.println(++z);
        refresh(chart);
        delay(delay, ()->
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

    /**
     * Copied by <a href="https://stackoverflow.com/questions/26454149/make-javafx-wait-and-continue-with-code">stackoverflow</a>
     * @param millis
     * @param continuation
     */
    public static void delay(long millis, Runnable continuation) {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try { Thread.sleep(millis); }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> continuation.run());
        new Thread(sleeper).start();
    }

    public void refresh(LineChart<String, Number> chart) throws IOException {
        XYChart.Series<String, Number> series;
        if(chart.getData().size()==0) {
            series = new XYChart.Series<>();
            chart.getData().add(series);
        }
        else series = chart.getData().get(0);

        readFile(chart, series);
    }

    private void readFile(LineChart<String, Number> chart, XYChart.Series<String, Number> series) throws IOException {
        FileReader reader = new FileReader(pfad);
        String line = reader.readLine();
        int i = 0;
        while( line!=null) {
            refresh(chart, series, line, i);
            i++;
            line = reader.readLine();
        }
        for(; i< series.getData().size(); i++)
            series.getData().remove(i);
    }

    private void refresh(LineChart<String, Number> chart, XYChart.Series<String, Number> series, String line, int i) throws IOException {
        System.out.println(line + '\t' + i);
        if(line.isEmpty()) return;
        String[] sep = line.split(";");
        if(sep.length!=2) throw new IOException(line + " entspricht nicht dem Format HH:MM;ZZZZ");
        try {
            int verg = Integer.parseInt(sep[1]);
            if(series.getData().size()>i) {
                if (!(series.getData().get(i).getYValue().equals(verg)))
                    series.getData().set(i, new XYChart.Data<>(sep[0], verg));
            }
            else
                series.getData().add(new XYChart.Data<>(sep[0], verg));
        }
        catch ( NumberFormatException e) {
            throw new IOException(line + " entspricht nicht dem Format HH:MM;ZZZZ");
        }
    }
}