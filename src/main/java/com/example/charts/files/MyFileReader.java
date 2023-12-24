package com.example.charts.files;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;

public class MyFileReader extends BufferedReader{

    public MyFileReader(String input) throws IOException {
        super(Files.newBufferedReader(Paths.get(input)));
    }

    public String readAll() throws IOException {
        StringBuilder result = new StringBuilder();
        for(String line = readLine(); line!=null; line = readLine())
            result.append(line);
        return result.toString();
    }
}
