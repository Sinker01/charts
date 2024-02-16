package com;

public class GraphikMain {
    public static void main(String[] args) {
        GraphikApplication.main(args);

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> new ExceptionDialog(e).showAndWait());
    }
}
