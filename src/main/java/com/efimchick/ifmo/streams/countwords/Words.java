package com.efimchick.ifmo.streams.countwords;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Words {
    private static final Pattern WORD_PATTERN = Pattern.compile("[^\\p{L}]+");
    private static final int MINIMAL_WORD_LENGTH = 4;
    private static final int MINIMAL_WORD_FREQUENCY = 10;

    public String countWords(List<String> lines) {
        return mapToString(Arrays.stream(getWordsArray(lines)).filter(x -> x.length() >= MINIMAL_WORD_LENGTH)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(e -> 1))));
    }

    private String[] getWordsArray(List<String> lines) {
        return WORD_PATTERN.split(lines.toString().toLowerCase(Locale.ROOT));
    }

    private String mapToString(Map<String, Integer> map) {
        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
        map.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        return sortedMap.entrySet().stream().filter(x -> x.getValue() >= MINIMAL_WORD_FREQUENCY)
                .map(key -> key.getKey() + " - " + key.getValue())
                .collect(Collectors.joining("\n"));
    }

}
