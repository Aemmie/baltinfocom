package ru.aemmie.baltinfocom;

import ru.aemmie.baltinfocom.parser.CSVParser;
import ru.aemmie.baltinfocom.parser.GroupParser;
import ru.aemmie.baltinfocom.parser.GroupWriter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 3) {
            System.err.println("Insufficient arguments amount!");
            System.err.println("Please, provide <in file> <out file> <number of columns> arguments");
            System.err.println("Example: lng.csv out.txt 3");
            System.exit(-1);
        }

        String in = args[0];
        String out = args[1];
        int columns = Integer.parseInt(args[2]);

        CSVParser csvParser = new CSVParser(in).filter(columns);
        GroupParser groupParser = new GroupParser(csvParser.getStream(), columns)
                .divideToGroups()
                .orderByGroupSize();
        GroupWriter.writeToFile(out, groupParser.getResults());
    }
}
