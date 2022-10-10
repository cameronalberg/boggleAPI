package com.example.boggle;

import com.example.boggle.game.data.BoggleDictionary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BoggleDictionaryTests {
    BoggleDictionary dictionary;

    @BeforeEach
    public void setup() {
        dictionary = new BoggleDictionary();
        dictionary.populateDictionary("dictionary_large.txt", "src/main" +
                "/resources" +
                "/data");
    }

    @Test
    void checkForWord1() {
        Assertions.assertTrue(dictionary.isWord("sin"));
        Assertions.assertTrue(dictionary.incompleteWord("sin"));
    }

    @Test
    void checkForPlurals() {
        Assertions.assertTrue(dictionary.isWord("umbrella"));
        Assertions.assertTrue(dictionary.incompleteWord("umbrella"));
        Assertions.assertTrue(dictionary.isWord("umbrellas"));
    }


    @Test
    void checkInvalidWordsDoNotExist() {
        Assertions.assertFalse(dictionary.isWord("as"));
        Assertions.assertFalse(dictionary.isWord("23"));
        Assertions.assertFalse(dictionary.isWord("23"));
        Assertions.assertFalse(dictionary.isWord(""));
        Assertions.assertFalse(dictionary.isWord("raxy"));
    }

}
