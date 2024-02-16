package com;

public class FlascheMain {
    public static void main(String[] args) {
        FlascheApplication.main(args);

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> new ExceptionDialog(e).showAndWait());
    }
}
