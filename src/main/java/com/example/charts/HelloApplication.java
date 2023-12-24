package com.example.charts;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.example.charts.files.ConfigReader;
import com.example.charts.files.MyFileReader;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HelloApplication extends ConfigApplication {
    public String pfad;
    private AreaChart<String, Number> chart;

    private CategoryAxis xAxis;

    public HelloApplication() {

        this.xAxis = new CategoryAxis();
        this.xAxis.tickLabelFontProperty().set(Font.font(20.0D));
        this.xAxis.setAutoRanging(false);

        NumberAxis yAxis = new NumberAxis();
        yAxis.tickLabelFontProperty().set(Font.font(20.0D));

        this.chart = new AreaChart<>(this.xAxis, yAxis);
        this.chart.setLegendVisible(false);
    }

    @Override
    protected Scene createScene() {
        BorderPane pane = new BorderPane();
        pane.setCenter((Node)this.chart);
        Scene scene = new Scene((Parent)pane);
        scene.getStylesheets().add("style.css");
        return scene;
    }

    @Override
    protected String getTitle() {
        return "Berg";
    }

    @Override
    protected void refresh() {
        XYChart.Series<String, Number> series;
        if (chart.getData().size() == 0) {
            series = new XYChart.Series();
            series.setName("Uhrzeit");
            chart.getData().add(series);
        } else {
            series = (XYChart.Series<String, Number>)chart.getData().get(0);
        }
        try {
            readFile(chart, series);
        } catch (IOException e) {
            new ExceptionDialog(e).show();
        }
    }

    private void readFile(AreaChart<String, Number> chart, XYChart.Series<String, Number> series) throws IOException {
        MyFileReader reader = new MyFileReader(this.pfad);
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



    public static void main(String[] args) {
        launch(args);
    }
}
