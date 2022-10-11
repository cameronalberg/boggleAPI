package com.example.boggle.game.board;

import com.example.boggle.response.SolvedBoardResponse;
import com.example.boggle.game.solver.BoggleTraversal;
import com.example.boggle.game.solver.WordPath;
import com.example.boggle.game.data.TrieDictionary;

import java.util.*;

public class BoggleBoard {
    private static final char[][] diceConfig = {
            //NEED TO ADD CONDITION FOR Q LETTER TO ADD U
            {'A', 'E', 'A', 'N', 'E', 'G'},
            {'W', 'N', 'G', 'E', 'E', 'H',},
            {'A', 'H', 'S', 'P', 'C', 'O'},
            {'L', 'N', 'H', 'N', 'R', 'Z'},
            {'A', 'S', 'P', 'F', 'F', 'K'},
            {'T', 'S', 'T', 'I', 'Y', 'D'},
            {'O', 'B', 'J', 'O', 'A', 'B'},
            {'O', 'W', 'T', 'O', 'A', 'T'},
            {'I', 'O', 'T', 'M', 'U', 'C'},
            {'E', 'R', 'T', 'T', 'Y', 'L'},
            {'R', 'Y', 'V', 'D', 'E', 'L'},
            {'T', 'O', 'E', 'S', 'S', 'I'},
            {'L', 'R', 'E', 'I', 'X', 'D'},
            {'T', 'E', 'R', 'W', 'H', 'V'},
            {'E', 'I', 'U', 'N', 'E', 'S'},
            {'N', 'U', 'I', 'H', 'M', 'Q'}
    };
    private final static int rawDieCount = 16;
    private final int boardSize;
    private final ArrayList<BoggleDie> dice;
    private final int[][] grid;
    private BoggleTraversal searcher;

    public BoggleBoard(int boardSize) {
        this.boardSize = boardSize;
        int numDice = boardSize * boardSize;
        this.grid = buildGrid(boardSize);
        this.dice = new ArrayList<>();
        for (int i = 0; i < numDice; i++) {
            BoggleDie die = new BoggleDie((diceConfig[i % rawDieCount]), i);
            this.dice.add(die);
        }
        this.shuffleBoard();
    }

    public BoggleBoard(String inputBoard) {
        this.boardSize = (int) Math.sqrt(inputBoard.length());
        this.dice = new ArrayList<>();
        this.grid = buildGrid(boardSize);
        for (int i = 0; i < inputBoard.length(); i++) {
            BoggleDie die = new BoggleDie(inputBoard.charAt(i), i);
            this.dice.add(die);
        }
        this.assignNeighbors();
    }

    public static String validate(String inputBoard) {
        if (inputBoard == null || inputBoard.isBlank()) {
            return null;
        }
        double size = Math.sqrt(inputBoard.length());
        if (!(size == (int) size)) {
            return null;
        } else if (!(inputBoard.matches("^[a-zA-Z]*$"))) {
            return null;
        }
        return inputBoard.toUpperCase();
    }

    public char getChar(int index) {
        BoggleDie die = this.dice.get(index);
        return die.getActiveLetter();
    }

    public BoggleDie getDie(int index) {
        return this.dice.get(index);
    }

    public void shuffleBoard() {
        this.searcher = null;
        Collections.shuffle(this.dice, new Random());
        int i = 0;
        for (BoggleDie die : this.dice) {
            die.rollDie();
            die.setActiveIndex(i++);
        }
        this.assignNeighbors();
    }

    private void assignNeighbors() {
        for (int i = 0; i < dice.size(); i++) {
            ArrayList<Integer> neighbors = neighborIndexes(i, this.boardSize);
            BoggleDie die = dice.get(i);
            die.addNeighbors(this.findNeighbors(neighbors));
        }
    }

    private ArrayList<BoggleDie> findNeighbors(ArrayList<Integer> indices) {
        ArrayList<BoggleDie> neighbors = new ArrayList<>();
        if (indices == null) {
            return null;
        }
        for (int i : indices) {
            neighbors.add(dice.get(i));
        }
        return neighbors;
    }

    private ArrayList<Integer> neighborIndexes(int index, int boardSize) {
        //convert index to grid position based on board size
        ArrayList<Integer> results = new ArrayList<>();
        int x = index % boardSize;
        int y = index / boardSize;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (inBounds(y + i, x + j)) {
                    results.add(this.grid[i + y][j + x]);
                }
            }
        }
        return results;
    }


    public String getNeighbors(int index) {
        BoggleDie die = dice.get(index);
        return die.getNeighbors().toString();
    }

    private boolean inBounds(int x, int y) {
        return !(x < 0 || x >= boardSize || y < 0 || y >= boardSize);
    }

    private static int[][] buildGrid(int boardSize) {
        int[][] grid = new int[boardSize][boardSize];
        int k = 0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                grid[i][j] = k++;
            }
        }
        return grid;
    }

    public ArrayList<BoggleDie> getDice() {
        return new ArrayList<>(this.dice);
    }

    public void printBoard() {
        StringBuilder linebreak = new StringBuilder();
        linebreak.append("-".repeat(Math.max(0, boardSize)));
        System.out.println("\n" + linebreak);
        System.out.println(this);
        System.out.println(linebreak + "\n");
    }

    public void search(TrieDictionary dictionary) {
        this.searcher = new BoggleTraversal(dictionary, this);
        this.searcher.traverse();
    }

    public SolvedBoardResponse getSolution(TrieDictionary dictionary) {
        this.search(dictionary);
        List<WordPath> words = this.getFoundWords();
        String time = this.searcher.getLastSearchTime();
        int score = this.searcher.getScore();
        SolvedBoardResponse response = new SolvedBoardResponse(words,
                this.toString());
        response.setTime(time);
        response.setScore(score);
        return response;
    }

    public List<WordPath> getFoundWords() {
        return this.searcher.getFoundWords();
    }

    public void printWords() {
        List<WordPath> results = this.searcher.getFoundWords();
        for (WordPath result : results) {
            System.out.println(result);
        }
    }

    public String toFormattedString() {
        StringBuilder output = new StringBuilder();
        int k = 0;
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                char letter = this.getChar(k++);
                output.append(letter);
                if (letter == 'Q') {
                    output.append('u');
                }
                if (j < this.boardSize - 1) {
                    output.append("-");
                }
            }
            if (i < this.boardSize - 1) {
                output.append("\n");
            }
        }
        return output.toString();
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        int k = 0;
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                char letter = this.getChar(k++);
                output.append(letter);
                if (letter == 'Q') {
                    output.append('u');
                }
                if (!(i == this.boardSize - 1 && j == this.boardSize - 1)) {
                    output.append("-");
                }
            }
        }
        return output.toString();
    }
}
