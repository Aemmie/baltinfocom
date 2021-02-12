package ru.aemmie.baltinfocom.parser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Set;

public class GroupWriter {
    public static void writeToFile(String file, Collection<Set<String>> sortedResults) throws IOException {
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println("Groups with more than 1 element: " +
                    sortedResults.stream().filter(c -> c.size() > 1).count());
            writer.println("Total groups: " + sortedResults.size());
            writer.println();

            int counter = 0;
            for (Collection<String> group : sortedResults) {
                writer.println("Group " + (++counter));
                for (String s : group) writer.println(s);
                writer.println();
            }
        }
    }
}
