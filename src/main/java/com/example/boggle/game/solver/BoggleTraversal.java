package com.example.boggle.game.solver;

import com.example.boggle.game.data.TrieDictionary;
import com.example.boggle.game.board.BoggleBoard;
import com.example.boggle.game.board.BoggleDie;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public class BoggleTraversal {
    private TrieDictionary dictionary;
    private Set<WordPath> foundWords;
    private BoggleBoard board;
    private int score;
    private long lastSearchTime;

    public BoggleTraversal(TrieDictionary dictionary, BoggleBoard board) {
        this.dictionary = dictionary;
        this.board = board;
        this.foundWords = new HashSet<>();
        this.score = 0;
        this.lastSearchTime = 0;
    }

    public int numWordsFound() {
        return this.foundWords.size();
    }

    public List<WordPath> getFoundWords() {
        List<WordPath> list = new ArrayList<>(this.foundWords);
        Collections.sort(list);
        return list;
    }

    public int getScore() {
        return this.score;
    }

    public void traverse() {
        this.lastSearchTime = 0;
        long startTime = System.nanoTime();
        ArrayList<BoggleDie> dice = board.getDice();
        for (BoggleDie die : dice) {
            traverse(die);
        }
        long endTime = System.nanoTime();
        this.lastSearchTime = (endTime - startTime) / 1000;
    }

    private void traverse(BoggleDie die) {
        Set<BoggleDie> visited = new HashSet<>();
        WordPath wordpath = new WordPath();
        DFS(die, visited, wordpath);
    }

    public String getLastSearchTime() {
        NumberFormat formatter = new DecimalFormat("#0.00");
        return formatter.format(this.lastSearchTime / 1000d);
    }

    private void updateScore(int length) {
        int value = 11;

        switch (length) {
            case 3:
            case 4:
                value = 1;
                break;
            case 5:
                value = 2;
                break;
            case 6:
                value = 3;
                break;
            case 7:
                value = 5;
                break;
        }

        this.score += value;
    }

    private void DFS(BoggleDie die, Set<BoggleDie> visited, WordPath wordpath) {
        visited.add(die);
        wordpath.addToPath(die);
        String word = wordpath.getWord();
        if (this.dictionary.isWord(word)) {
            if (!this.foundWords.contains(wordpath)) {
                this.foundWords.add(wordpath);
                updateScore(word.length());
            }
        }

        if (!this.dictionary.incompleteWord(word)) {
            visited.remove(die);
            return;
        }
        for (BoggleDie neighbor : die.getNeighbors()) {
            if (!visited.contains(neighbor)) {
                WordPath newWordPath = new WordPath(wordpath);
                DFS(neighbor, visited, newWordPath);
            }
        }
        visited.remove(die);

    }


}
