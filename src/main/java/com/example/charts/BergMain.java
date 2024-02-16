package com.example.charts;

public class BergMain {
    public static void main(String[] args) {
        HelloApplication.main(args);

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> new ExceptionDialog(e).showAndWait());
    }
}
