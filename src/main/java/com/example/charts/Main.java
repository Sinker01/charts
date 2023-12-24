package com.example.charts;

public class Main {
    public static void main(String[] args) {
        BottleDrawing.main(args);

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                new ExceptionDialog(e).showAndWait();
            }
        });
    }
}
