package com.example.charts;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private LineChart lineChart;

    public HelloController() {
        System.out.println("test");
    }

    @FXML
    public void initialize() {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}