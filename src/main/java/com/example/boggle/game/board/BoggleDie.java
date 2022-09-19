package com.example.boggle.game.board;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;

public class BoggleDie {

    private char activeLetter;
    private final char[] letters;
    private int activeIndex;
    private final int ID;
    private Set<BoggleDie> neighbors;

    public BoggleDie(char[] letters, int id) {
        this.neighbors = new HashSet<>();
        this.ID = id;
        this.letters = letters;
        this.activeLetter = letters[0];
    }
    public BoggleDie(char letter, int id) {
        this.neighbors = new HashSet<>();
        this.ID = id;
        this.letters = new char[letter];
        this.activeLetter = letters[0];
    }

    public void rollDie() {
        Random random = new Random(123091823);
        int index = random.nextInt(6);
        this.activeLetter = letters[index];
        this.clearNeighbors();
    }

    public void setActiveIndex(int index) {
        this.activeIndex = index;
    }

    public char getActiveLetter() {
        return this.activeLetter;
    }

    public int getActiveIndex() {
        return this.activeIndex;
    }

    public int getID() {
        return this.ID;
    }

    public void addNeighbors(ArrayList<BoggleDie> dice) {
        if (dice == null) {
            return;
        }
        for (BoggleDie die : dice) {
            if (die != this) {
                this.neighbors.add(die);
            }
        }
    }

    public ArrayList<BoggleDie> getNeighbors() {
        return new ArrayList<>(this.neighbors);
    }

    public void clearNeighbors() {
        this.neighbors = new HashSet<>();
    }

    @Override
    public String toString() {
        String output = "";
        output += this.activeLetter;
        if (this.activeLetter == 'Q') {
            output += 'u';
        }
        return output;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof BoggleDie)) {
            return false;
        }

        BoggleDie temp = (BoggleDie) o;
        return this.getID() == temp.getID();
    }
}
