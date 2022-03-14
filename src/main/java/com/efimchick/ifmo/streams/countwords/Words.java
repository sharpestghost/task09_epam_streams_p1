package com.efimchick.ifmo.streams.countwords;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Words {

    private static final Pattern WORD_PATTERN = Pattern.compile("[^\\p{L}]+");
    private static final int MINIMAL_WORD_LENGTH = 4;
    private static final int MINIMAL_WORD_FREQUENCY = 10;
    private final Map<String, Integer> map = new HashMap<>();

    public String countWords(Iterable<String> lines) {
        for (String s: lines) {
            String[] words = WORD_PATTERN.split(s.toLowerCase(Locale.ROOT));
            for (String word: words) {
                addWordToMap(word);
            }
        }
        return mapToString(map);
    }

    private void addWordToMap(String word) {
        if (word.length() >= MINIMAL_WORD_LENGTH) {
            if (!map.containsKey(word)) {
                map.put(word, 1);
            } else {
                int count = map.get(word);
                map.put(word, count + 1);
            }
        }
    }

    private String mapToString(Map<String, Integer> map) {
        LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();
        map.entrySet()
                .stream().sorted(Map.Entry.comparingByKey())
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
        return reverseSortedMap.entrySet().stream().filter(x -> x.getValue() >= MINIMAL_WORD_FREQUENCY)
                .map(key -> key.getKey().toLowerCase(Locale.ROOT) + " - " + key.getValue())
                .collect(Collectors.joining("\n"));
    }

}
