package com.example.charts.files;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class MyFileReader extends BufferedReader implements Iterable<String>{

    public MyFileReader(String input) throws IOException {
        super(Files.newBufferedReader(Paths.get(input)));
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<>() {
            private String next = null;
            @Override
            public boolean hasNext() {
                try {
                    next = readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return next!=null;
            }

            @Override
            public String next() {
                return next;
            }
        };
    }
}
