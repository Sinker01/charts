package com.example.charts;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    public String pfad;

    public LocalTime start;

    public LocalTime ende;

    public int intervall;

    private static final int FONT_SIZE = 20;

    private AreaChart<String, Number> chart;

    private CategoryAxis xAxis;

    public void start(Stage stage) throws IOException {
        init_config();
        this.xAxis = new CategoryAxis();
        this.xAxis.tickLabelFontProperty().set(Font.font(20.0D));
        NumberAxis yAxis = new NumberAxis();
        yAxis.tickLabelFontProperty().set(Font.font(20.0D));
        this.chart = new AreaChart((Axis)this.xAxis, (Axis)yAxis);
        this.chart.setLegendVisible(false);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
        this.xAxis.setAutoRanging(false);
        BorderPane pane = new BorderPane();
        pane.setCenter((Node)this.chart);
        Scene scene = new Scene((Parent)pane);
        scene.getStylesheets().add("style.css");
        stage.setTitle("Berg");
        stage.setScene(scene);
        stage.setFullScreen(true);
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
        for (String line = rd.readLine(); line != null; line = rd.readLine()) {
            if (!line.isEmpty()) {
                i++;
                String[] split = line.split(" ");
                if (split.length != 2)
                    throw new IOException("Linie " + i + " mit dem Inhalt " + line + " im config.ini falsch formatiert.");
                switch (split[0]) {
                    case "Verz:":
                        this.pfad = split[1];
                        break;
                    case "Start:":
                        this.start = LocalTime.parse(split[1]);
                        break;
                    case "Ende:":
                        this.ende = LocalTime.parse(split[1]);
                        break;
                    case "Intervall:":
                        this.intervall = Integer.parseInt(split[1]);
                        break;
                    default:
                        //throw new IOException("Linie " + i + " mit dem Inhalt " + line + " im config.ini falsch formatiert.");
                        i--;
                }
            }
        }
        if (i != 4)
            throw new IOException("Zu wenig Argumente in config.ini");
    }

    private int z = 0;

    private void endLoop() throws IOException {
        refresh(this.chart);
        delay(this.intervall, () -> {
            try {
                endLoop();
            } catch (IOException iOException) {}
        });
    }

    public static void delay(final long millis, Runnable continuation) {
        Task<Void> sleeper = new Task<Void>() {
            protected Void call() throws Exception {
                try {
                    Thread.sleep(millis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> continuation.run());
        (new Thread((Runnable)sleeper)).start();
    }

    public void refresh(AreaChart<String, Number> chart) throws IOException {
        XYChart.Series<String, Number> series;
        if (chart.getData().size() == 0) {
            series = new XYChart.Series();
            series.setName("Uhrzeit");
            chart.getData().add(series);
        } else {
            series = (XYChart.Series<String, Number>)chart.getData().get(0);
        }
        readFile(chart, series);
    }

    private void readFile(AreaChart<String, Number> chart, XYChart.Series<String, Number> series) throws IOException {
        FileReader reader = new FileReader(this.pfad);
        String line = reader.readLine();
        int i = 0;
        while (line != null) {
            refresh(chart, series, line, i);
            i++;
            line = reader.readLine();
        }
        for (; i < series.getData().size(); i++)
            series.getData().remove(i);
    }

    private void refresh(AreaChart<String, Number> chart, XYChart.Series<String, Number> series, String line, int i) throws IOException {
        if (line.isEmpty())
            return;
        String[] sep = line.split(";");
        if (sep.length != 2)
            throw new IOException(line + " entspricht nicht dem Format HH:MM;ZZZZ");
        try {
            int verg = Integer.parseInt(sep[1]);
            if (series.getData().size() > i) {
                if (((Number)((XYChart.Data)series.getData().get(i)).getYValue()).intValue() != verg)
                    series.getData().set(i, new XYChart.Data(sep[0], Integer.valueOf(verg)));
            } else {
                this.xAxis.getCategories().add(sep[0]);
                series.getData().add(new XYChart.Data(sep[0], Integer.valueOf(verg)));
            }
        } catch (NumberFormatException e) {
            throw new IOException(line + " entspricht nicht dem Format HH:MM;ZZZZ");
        }
    }
}
