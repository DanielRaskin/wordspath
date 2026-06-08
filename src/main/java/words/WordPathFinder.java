package words;

import java.util.*;

public class WordPathFinder {
    // pattern -> all words match this pattern, e.g.
    // "с_он" -> ["слон", "стон", ...]
    private Map<String, List<String>> words;
    private Set<String> wordSet;

    // map initialization
    public WordPathFinder(String allWords) {
        words = new HashMap<>();
        wordSet = new HashSet<>();
        for (var word : allWords.split("\\s")) {
            wordSet.add(word);
            for (String pattern : getAllPatterns(word)) {
                var list = words.get(pattern);
                if (list == null) {
                    list = new ArrayList<>();
                    words.put(pattern, list);
                }
                list.add(word);
            }
        }
    }

    public List<String> findPath(String from, String to) {
        if ((!wordSet.contains(from)) || (!wordSet.contains(to))) {
            return null;
        }
        if (from.length() != to.length()) {
            return null;
        }

        // already processed words
        Set<String> visited = new HashSet<>();
        // word paths from the last step
        // like ["слон", "стон", "стоп"]
        List<WordList> latest = new ArrayList<>();
        latest.add(new WordList(from, null));
        while (! latest.isEmpty()) {
            var current = latest;
            latest = new ArrayList<>();
            for (var wordPath : current) {
                // for each word path take the last word
                for (var pattern : getAllPatterns(wordPath.word())) {
                    // for each last word take all available patterns
                    // like "слон" -> ["_лон", "с_он", "сл_н", "сло_"]
                    for (var newWord : words.get(pattern)) {
                        // for each pattern process all words match this pattern
                        // like "с_он" -> ["слон", "стон", ...]
                        if (! visited.contains(newWord)) {
                            // if word wasn't processed
                            // create new word path this word
                            var newWordPath = new WordList(newWord, wordPath);
                            if (newWord.equals(to)) {
                                // we found word we should reach!
                                return newWordPath.toList();
                            }
                            visited.add(newWord);
                            latest.add(newWordPath);
                        }
                    }
                }
            }
        }
        // we made the full search but didn't find word we should reach
        return null;
    }

    private static List<String> getAllPatterns(String word) {
        var result = new ArrayList<String>();
        for (int i = 0; i < word.length(); i++) {
            result.add(word.substring(0, i) + "_" + word.substring(i + 1));
        }
        return result;
    }
}
