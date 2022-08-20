
import org.sqlite.util.StringUtils;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class TrieDictionary implements Dictionary{
    private TrieNode root;
    private Set<String> wordSet;
    private int minWordLength;

    public TrieDictionary() {
        this.root = new TrieNode();
        this.wordSet = new HashSet<>();
        this.minWordLength = 3;
    }

    public int wordCount() {
        return this.wordSet.size();
    }

    @Override
    public void addWord(String input) {
        if (input.length() < minWordLength) {
            return;
        }

        if (!input.matches("[a-zA-Z]+")) {
            return;
        }
        String word = input.toLowerCase();
        TrieNode current = this.root;
        int length = word.length();

        for (int i = 0; i < length; i++) {
            char letter = word.charAt(i);
            TrieNode nextNode = current.get(letter);
            if (nextNode == null) {
                nextNode = new TrieNode();
                current.addChild(letter, nextNode);
            }
            current = nextNode;
        }
        current.setAsValidWord();
        this.wordSet.add(word);
    }

    private static String convertToLowerCase(String word) {
        String converted = word;
        converted = converted.toLowerCase();
        return converted;
    }

    @Override
    public boolean isWord(String word) {
        String converted = convertToLowerCase(word);

        return this.wordSet.contains(converted);
    }

    @Override
    public boolean incompleteWord(String word) {
        String converted = convertToLowerCase(word);
        TrieNode current = root;
        int length = converted.length();

        for (int i = 0; i < length; i++) {
            char letter = converted.charAt(i);
            TrieNode nextNode = current.get(letter);
            if (nextNode == null) {
                return false;
            }
            current = nextNode;
        }
        return current.hasChildren();
    }


}
