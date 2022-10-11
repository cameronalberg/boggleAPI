package com.example.boggle.response;

import com.example.boggle.game.solver.WordPath;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

public class SolvedBoardResponse {

    @Schema(example = "u-e-t-b")
    String board;

    @Schema(example = "1")
    int score;

    @Schema(example = "[{\"word\": \"vet\",\"path\":[3,1,2]}]")
    List<WordPath> words;

    @JsonProperty("words_found")
    @Schema(example = "1")
    int numWordsFound;

    @JsonProperty("search_time")
    @Schema(example = "0.05")
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
