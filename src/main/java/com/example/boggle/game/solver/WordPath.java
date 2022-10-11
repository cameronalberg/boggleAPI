package com.example.boggle.game.solver;

import com.example.boggle.game.board.BoggleDie;

import java.util.ArrayList;
import java.util.Objects;

public class WordPath implements Comparable<WordPath> {
    String word;
    ArrayList<Integer> path;

    public WordPath() {
        this.word = "";
        this.path = new ArrayList<>();
    }

    public WordPath(WordPath wp) {
        this.word = wp.getWord();
        this.path = new ArrayList<>(wp.getPath());
    }

    public void addToPath(BoggleDie die) {
        char letter = die.getActiveLetter();
        this.word += letter;
        if (letter == 'Q') {
            this.word += 'U';
        }

        path.add(die.getActiveIndex());
    }

    public String getWord() {
        return this.word;
    }

    public ArrayList<Integer> getPath() {
        return new ArrayList<>(this.path);
    }

    @Override
    public String toString() {
        String output = word + ": ";
        output += path.toString();
        return output;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }


        if (!(o instanceof WordPath)) {
            return false;
        }

        WordPath temp = (WordPath) o;
        return Objects.equals(this.getWord(), temp.getWord());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getWord());
    }

    @Override
    public int compareTo(WordPath o) {
        String current = this.getWord();
        String compared = o.getWord();
        return current.compareTo(compared);
    }
}
