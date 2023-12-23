package com.example.charts;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;

public class FileReader extends BufferedReader{
    public static String pfad;
    public static LocalTime start;
    public static LocalTime ende;
    public static int intervall;

    public FileReader(String input) throws IOException {
        super(Files.newBufferedReader(Paths.get(input)));
    }

    static void init_config(String name) throws IOException {
        FileReader rd = new FileReader("files/config.ini");
        int i = 0;
        for(String line = rd.readLine(); line != null; line = rd.readLine()) {
            if(line.isEmpty()) continue;
            i++;
            String[] split = line.split(" ");
            if(split.length!=2) throw new IOException("Linie " + i + " mit dem Inhalt " + line + " im config.ini falsch formatiert.");
            switch (split[0]) {
                case "Start:":
                    start =LocalTime.parse(split[1]);
                    break;
                case "Ende:":
                    ende = LocalTime.parse(split[1]);
                    break;
                case "Intervall:":
                    intervall = Integer.parseInt(split[1]);
                    break;
                default:
                    if(name.equals(split[0])) {
                        pfad = split[1];
                        break;
                    }
                    i--;
                    // IOException("Linie " + i + " mit dem Inhalt " + line + " im config.ini falsch formatiert.");
            }
        }
        if(i!=4 ) throw new IOException("Zu wenig Argumente in config.ini");
    }

    public String readAll() throws IOException {
        StringBuilder result = new StringBuilder();
        for(String line = readLine(); line!=null; line = readLine())
            result.append(line);
        return result.toString();
    }
}
