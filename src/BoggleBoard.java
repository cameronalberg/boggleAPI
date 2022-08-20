import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BoggleBoard {
    private static char[][] diceConfig = {
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
    private int boardSize;
    private ArrayList<BoggleDie> dice;
    private int numDice;
    private int grid[][];
    private BoggleTraversal searcher;

    public BoggleBoard(int boardSize) {
        this.boardSize = boardSize;
        this.numDice = boardSize * boardSize;
        this.grid = buildGrid(boardSize);
        this.dice = new ArrayList<>();
        for (int i = 0; i < this.numDice; i++) {
            BoggleDie die = new BoggleDie(diceConfig[i], i);
            this.dice.add(die);
        }
        this.shuffleBoard();
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
        Collections.shuffle(this.dice, new Random(123091823));
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
                if (inBounds(y+i,x+j)) {
                    results.add(this.grid[i+y][j+x]);
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
        for (int i = 0; i < boardSize; i++) {
            linebreak.append("-");
        }
        System.out.println("\n" + linebreak);
        System.out.println(this);
        System.out.println(linebreak + "\n");
    }

    public void search(TrieDictionary dictionary) {
        this.searcher = new BoggleTraversal(dictionary, this);
        this.searcher.traverse();
    }

    public void printStats() {
        System.out.println("Time elapsed (ms): " + this.searcher.getLastSearchTime());
        System.out.println("Words Found: " + this.searcher.numWordsFound());
        System.out.println("Total Score: " + this.searcher.getScore());
        System.out.println("");
    }

    public void printWords() {
        List<WordPath> results = this.searcher.getFoundWords();
        for (WordPath result : results) {
            System.out.println(result);
        }
    }

    @Override
    public String toString() {
        String output = "";
        int k = 0;
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                char letter =  this.getChar(k++);
                output += letter;
                if (letter == 'Q') {
                    output += 'u';
                }
                if (j < this.boardSize - 1) {
                    output += "-";
                }
            }
            if (i < this.boardSize - 1) {
                output += "\n";
            }
        }
        return output;
    }
}
