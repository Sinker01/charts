package com.example.charts;

import java.io.IOException;

import com.example.charts.files.MyFileReader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

public class HelloApplication extends ConfigApplication {
    private final AreaChart<String, Number> chart;

    private final CategoryAxis xAxis;

    private final XYChart.Series<String, Number> series;

    public HelloApplication() {

        this.xAxis = new CategoryAxis();
        this.xAxis.tickLabelFontProperty().set(Font.font(20.0D));
        this.xAxis.setAutoRanging(false);

        NumberAxis yAxis = new NumberAxis();
        yAxis.tickLabelFontProperty().set(Font.font(20.0D));

        this.chart = new AreaChart<>(this.xAxis, yAxis);
        this.chart.setLegendVisible(false);

        series = new XYChart.Series<>();
        series.setName("Uhrzeit");

        chart.getData().add(series);
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
        xAxis.getCategories().clear();
        series.getData().clear();
        try {
            readFile(series);
        } catch (IOException e) {
            new ExceptionDialog(e).show();
        }
    }

    private void readFile(XYChart.Series<String, Number> series) throws IOException {
        int line_count = 0;
        try (MyFileReader reader = new MyFileReader(configReader.get("Berg"))) {
            for (String line: reader) {
                line_count++;
                if(line.isEmpty()) continue;

                String[] sep = line.split(";");
                if (sep.length != 2) {
                    throw new IOException(line + " entspricht nicht dem Format HH:MM;ZZZZ");
                }

                try {
                    int compare = Integer.parseInt(sep[1]);
                    this.xAxis.getCategories().add(sep[0]);
                    series.getData().add(new XYChart.Data(sep[0], compare));
                }
                catch (NumberFormatException e) {
                    throw new IOException(line + " entspricht nicht dem Format HH:MM;ZZZZ");
                }
            }
        }
        catch (IllegalArgumentException e) {
            new ExceptionDialog(e).showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
