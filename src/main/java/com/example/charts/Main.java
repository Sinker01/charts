package com.example.charts;

public class Main {
    public static void main(String[] args) {
        BottleDrawing.main(args);

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> new ExceptionDialog(e).showAndWait());
    }
}
