package com.example.boggle.api;

import com.example.boggle.game.solver.WordPath;

import java.util.ArrayList;
import java.util.List;

public class SolvedBoardResponse {
    String board;
    int numWordsFound;
    int score;
    String time;
    List<WordPath> words;


    public SolvedBoardResponse(List<WordPath> words, String board) {
        this.words = words;
        this.board = board;
        this.numWordsFound = words.size();
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<WordPath> getWords() {
        return new ArrayList<>(words);
    }

    public String getBoard() {
        return this.board;
    }
    public int getNumWordsFound() {
        return this.numWordsFound;
    }

    public int getScore() {
        return this.score;
    }

    public String getTime() {
        return this.time;
    }
}
