package words;

import java.util.ArrayList;
import java.util.List;

// List of words: last word + previous list
record WordList(String word, WordList prev) {
    List<String> toList() {
        var list = (prev == null) ? new ArrayList<String>() : prev.toList();
        list.add(word);
        return list;
    }
}
