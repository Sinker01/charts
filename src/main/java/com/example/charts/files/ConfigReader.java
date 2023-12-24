package com.example.charts.files;

import java.io.IOException;
import java.util.HashMap;

public class ConfigReader extends HashMap<String, String> {
    private final String file;

    public ConfigReader(String file) throws IOException {
        this.file = file;
        int line_count = 0;
        try (MyFileReader rd = new MyFileReader(file)) {
            for (String line = rd.readLine(); line != null; line = rd.readLine()) {
                line_count++;
                if (line.isEmpty()) continue;

                String[] split = line.split(" ");
                if (split.length != 2)
                    throw new IOException("Linie " + line_count + " mit dem Inhalt " + line + " in " + line_count + " falsch formatiert.");

                super.put(split[0].replace(":", ""), split[1]);
            }
        }
    }

    @Override
    public String get(Object key) {
        if(!super.containsKey(key))
            throw new IllegalArgumentException("Argument " + key + " in " + file + " not Found");

        return super.get(key);
    }
}
