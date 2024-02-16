package com.flasche;

import com.ExceptionDialog;

/**
 * Main-Klasse, um das Flache-Programm zu starten
 */
public class FlascheMain {
    public static void main(String[] args) {
        FlascheApplication.main(args);

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> new ExceptionDialog(e).showAndWait());
    }
}
