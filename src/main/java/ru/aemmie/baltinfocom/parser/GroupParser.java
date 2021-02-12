package ru.aemmie.baltinfocom.parser;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GroupParser {
    private Stream<String> dataStream;
    private Stream<Set<String>> groupStream;
    private final int columns;


    public GroupParser(Stream<String> csvData, int columns) {
        this.dataStream = csvData;
        this.columns = columns;
    }

    public GroupParser divideToGroups() {
        if (dataStream == null) return this;

        ArrayList<HashMap<String, Integer>> maps = new ArrayList<>();
        HashMap<Integer, Set<String>> groups = new HashMap<>();
        Integer[] indexes = new Integer[columns];
        for (int i = 0; i < columns; i++) maps.add(new HashMap<>());

        dataStream.forEach(s -> {
            String[] parts = s.split(";", -1);  // -1 for valid split when we have "a;b;" (ending with ';')
            Set<String> set = null;

            Integer maxIndex = groups.size();
            Integer minIndex = maxIndex;

            for (int i = 0; i < columns; i++) {
                String str = parts[i];
                Integer groupNum = maps.get(i).get(str);
                if (groupNum != null) {
                    indexes[i] = groupNum;
                    set = groups.get(groupNum);
                    if (groupNum < minIndex) minIndex = groupNum;
                } else indexes[i] = maxIndex;
            }

            if (set == null) {
                set = new HashSet<>();
            }

            set.add(s);
            groups.put(minIndex, set);

            for (int i = 0; i < columns; i++) {
                if (!parts[i].equals("\"\"") && parts[i].length() > 0) {
                    Integer index = indexes[i];
                    if (index < maxIndex && !index.equals(minIndex)) {
                        set.addAll(groups.get(index));
                    }
                    maps.get(i).put(parts[i], minIndex);
                }
            }

        });

        Stream<Integer> groupIndexesStream = Stream.empty();
        for (int i = 0; i < columns; i++) {
            groupIndexesStream = Stream.concat(groupIndexesStream, maps.get(i).values().stream());
        }

        groupStream = groupIndexesStream.distinct().map(groups::get);
        dataStream = null;
        return this;
    }

    public GroupParser orderByGroupSize() {
        groupStream = groupStream.sorted((a1, a2) -> Integer.compare(a2.size(), a1.size()));
        return this;
    }

    public List<Set<String>> getResults() {
        if (groupStream == null) throw new RuntimeException("Method called before groups division or called twice");
        List<Set<String>> list = groupStream.collect(Collectors.toList());
        groupStream = null;
        return list;
    }
}
