import java.util.*;

public class BoggleTraversal {
    private TrieDictionary dictionary;
    private Set<WordPath> foundWords;
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

    public List<WordPath> getFoundWords() {
        List<WordPath> list = new ArrayList<>(this.foundWords);
        Collections.sort(list);
        return list;
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
