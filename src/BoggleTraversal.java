import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BoggleTraversal {
    private TrieDictionary dictionary;
    private Set<String> foundWords;
    private BoggleBoard board;
    private int score;

    public BoggleTraversal(TrieDictionary dictionary, BoggleBoard board) {
        this.dictionary = dictionary;
        this.board = board;
        this.foundWords = new HashSet<>();
        int score = 0;
    }

    public int numWordsFound() {
        return this.foundWords.size();
    }

    public int getScore() {
        return this.score;
    }
    public void traverse() {
        ArrayList<BoggleDie> dice = board.getDice();
        for (BoggleDie die : dice) {
            traverse(die);
        }
    }

    private void traverse(BoggleDie die) {
        Set<BoggleDie> visited = new HashSet<>();
        WordPath wordpath = new WordPath();
        DFS(die, visited, wordpath);
    }

    private void updateScore(int length) {
        int value = 11;

        switch(length) {
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

    private void DFS(BoggleDie die, Set<BoggleDie> visited, WordPath wordpath){
        visited.add(die);
        wordpath.addToPath(die);
        String word = wordpath.getWord();
        if (this.dictionary.isWord(word)) {
            if (!this.foundWords.contains(word)) {
                this.foundWords.add(word);
                updateScore(word.length());
                System.out.println(wordpath);
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
