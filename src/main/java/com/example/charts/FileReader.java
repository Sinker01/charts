package com.example.charts;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReader extends BufferedReader{
    public FileReader(String input) throws IOException {
        super(Files.newBufferedReader(Paths.get(input)));
    }

    public String readAll() throws IOException {
        StringBuilder result = new StringBuilder();
        for(String line = readLine(); line!=null; line = readLine())
            result.append(line);
        return result.toString();
    }
}
