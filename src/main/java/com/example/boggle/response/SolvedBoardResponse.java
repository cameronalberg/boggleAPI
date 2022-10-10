package com.example.boggle.response;

import com.example.boggle.game.solver.WordPath;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class SolvedBoardResponse {
    String board;
    int score;
    List<WordPath> words;

    @JsonProperty("words_found")
    int numWordsFound;

    @JsonProperty("search_time")
    String time;

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
