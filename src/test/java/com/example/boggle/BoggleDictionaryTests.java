package com.example.boggle;

import com.example.boggle.game.data.BoggleDictionary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BoggleDictionaryTests {
    final String DICT_DEFAULT = "dictionary.txt";
    final String PATH_DEFAULT = "src/main/resources/data";
    BoggleDictionary defaultDictionary;

    @BeforeEach
    public void setup() {
        defaultDictionary = new BoggleDictionary();
        defaultDictionary.populateDictionary(DICT_DEFAULT, PATH_DEFAULT);
    }

    @Test
    void checkEmptyDictionary() {
        BoggleDictionary emptyDictionary = new BoggleDictionary();
        Assertions.assertFalse(emptyDictionary.isWord("test"));
    }

    @Test
    void noParameterDictionary() {
        BoggleDictionary noParamDictionary = new BoggleDictionary();
        noParamDictionary.populateDictionary("", "");
        Assertions.assertTrue(noParamDictionary.isWord("test"));
        Assertions.assertTrue(noParamDictionary.incompleteWord("test"));
    }

    @Test
    void invalidDictionaryUsesDefault() {
        BoggleDictionary invalidDictionary = new BoggleDictionary();
        invalidDictionary.populateDictionary("bad.txt", "");
        Assertions.assertTrue(invalidDictionary.isWord("test"));
        Assertions.assertTrue(invalidDictionary.incompleteWord("test"));
    }

    @Test
    void checkForWord1() {
        Assertions.assertTrue(defaultDictionary.isWord("sin"));
        Assertions.assertTrue(defaultDictionary.incompleteWord("sin"));
    }

    @Test
    void checkForPlurals() {
        Assertions.assertTrue(defaultDictionary.isWord("umbrella"));
        Assertions.assertTrue(defaultDictionary.incompleteWord("umbrella"));
        Assertions.assertTrue(defaultDictionary.isWord("umbrellas"));
    }

    @Test
    void checkInvalidWordsDoNotExist() {
        Assertions.assertFalse(defaultDictionary.isWord("as"));
        Assertions.assertFalse(defaultDictionary.isWord("23"));
        Assertions.assertFalse(defaultDictionary.isWord("23"));
        Assertions.assertFalse(defaultDictionary.isWord(""));
        Assertions.assertFalse(defaultDictionary.isWord("raxy"));
    }

}
