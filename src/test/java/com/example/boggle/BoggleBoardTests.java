package com.example.boggle;

import com.example.boggle.game.board.BoggleBoard;
import com.example.boggle.game.data.BoggleDictionary;
import com.example.boggle.game.solver.WordPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class BoggleBoardTests {
    BoggleDictionary defaultDictionary;

    @BeforeEach
    public void setup() {
        defaultDictionary = new BoggleDictionary();
        defaultDictionary.populateDictionary(null, null);
    }

    @Test
    void checkQAddsU() {
        String board = "QAIL";
        BoggleBoard boggleBoard = new BoggleBoard(board);
        boggleBoard.getSolution(defaultDictionary.getDictionary());
        List<WordPath> words = boggleBoard.getFoundWords();
        List<String> expected = Arrays.asList("QUAIL", "AIL", "QUAI", "QUA");
        for (WordPath word : words) {
            Assertions.assertTrue(expected.contains(word.getWord()));
        }
    }

    @Test
    void checkPathIsCorrect() {
        String board = "QALI";
        BoggleBoard boggleBoard = new BoggleBoard(board);
        boggleBoard.getSolution(defaultDictionary.getDictionary());
        List<WordPath> words = boggleBoard.getFoundWords();
        String expectedWord = "QUAIL";
        List<Integer> expectedPath = Arrays.asList(0, 1, 3, 2);
        for (WordPath word : words) {
            if (word.getWord().equals(expectedWord)) {
                Assertions.assertEquals(word.getPath(), expectedPath);
            }
        }
    }

    @Test
    void checkBadBoardFindsNoWords() {
        String board = "XXXX";
        BoggleBoard boggleBoard = new BoggleBoard(board);
        boggleBoard.getSolution(defaultDictionary.getDictionary());
        List<WordPath> words = boggleBoard.getFoundWords();
        Assertions.assertEquals(0, words.size());
    }

    @Test
    void checkFoundWordCount() {
        String board = "QAIBZLATT";
        BoggleBoard boggleBoard = new BoggleBoard(board);
        boggleBoard.getSolution(defaultDictionary.getDictionary());
        List<WordPath> words = boggleBoard.getFoundWords();
        Assertions.assertEquals(14, words.size());
    }



}
