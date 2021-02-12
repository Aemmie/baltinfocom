package ru.aemmie.baltinfocom.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class CSVParser {

    private String regexCSVPart = "(\"[\\w\\.]*\"|)";
    private Stream<String> stream;

    public CSVParser(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        stream = Files.lines(path);
    }

    public CSVParser filter(int columns) {
        String pattern = String.format("^(%s;){%d}%s$", regexCSVPart, columns - 1, regexCSVPart);
        stream = stream.distinct().filter(s -> s.matches(pattern));
        return this;
    }

    public void setRegexPart(String regexCSVPart) {
        this.regexCSVPart = regexCSVPart;
    }

    public Stream<String> getStream() {
        return stream;
    }
}
