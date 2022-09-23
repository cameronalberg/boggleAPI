package com.example.boggle;

import com.example.boggle.api.SolvedBoardResponse;
import com.example.boggle.game.board.BoggleBoard;
import com.example.boggle.game.data.BoggleDictionary;
import com.example.boggle.game.solver.WordPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BoggleDictionaryTests {
    BoggleDictionary dictionary;
    final String inputBoard = "bssnohwen";

    @BeforeEach
    public void setup() {
        dictionary = new BoggleDictionary();
        dictionary.populateDictionary("dictionary_large.txt", "src/main" +
                "/resources" +
                "/data");
        BoggleBoard board = new BoggleBoard(inputBoard);
        System.out.println(board.toFormattedString());
        SolvedBoardResponse result =
                board.getSolution(dictionary.getDictionary());
        List<WordPath> words = result.getWords();
        for (WordPath word : words) {
            System.out.println(word.toString());
        }

    }

    @Test
    void checkForWord1() {
        assert(dictionary.isWord("sin"));
        assert(dictionary.incompleteWord("sin"));
    }

}
