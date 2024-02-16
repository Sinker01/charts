package com.files;

import java.io.IOException;
import java.util.HashMap;

public class ConfigReader {
    private final String file;
    private final HashMap<String, String> map = new HashMap<>();

    public ConfigReader() throws IOException {
        this("config.ini");
    }

    /**
     * Konstruktor, welcher bei Aufruf die Datei ausließt, die Im Konstruktor übergeben wurde.
     * @param file Die auszulesende Datei
     * @throws IOException Wenn die Datei nicht gelesen werden konnte
     * @throws IllegalArgumentException Wenn die Config-Datei falsch formatiert ist
     */
    public ConfigReader(String file) throws IOException, IllegalArgumentException {
        this.file = file;
        int line_count = 0; //Zähle aus Debug-Zwecken die Linien mit
        try (MyFileReader rd = new MyFileReader(file)) {
            for (String line: rd) {
                line_count++;
                if (line.isEmpty()) continue; //Leere Zeilen in der Config-Datei sind erlaubt

                String[] split = line.split(" ");
                if (split.length != 2)
                    throw new IllegalArgumentException("Linie " + line_count + " mit dem Inhalt " + line + " in " + this.file + " falsch formatiert.");

                map.put(split[0].replace(":", ""), split[1]);
            }
        }
    }

    /**
     * Gib den Wert der Config-datei für den Schlüssel zurück
     * @param key der zu suchende Schlüssel
     * @return Den Wert des Schlüssels
     * @throws IllegalArgumentException Wenn Schlüssel nicht in HashMap
     */
    public String get(String key) throws IllegalArgumentException {
        if(!map.containsKey(key))
            throw new IllegalArgumentException("Argument " + key + " in " + file + " not Found");

        return map.get(key);
    }
}
