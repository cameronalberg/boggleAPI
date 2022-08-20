import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BoggleTraversal {
    private TrieDictionary dictionary;
    private Set<String> foundWords;
    private BoggleBoard board;

    public BoggleTraversal(TrieDictionary dictionary, BoggleBoard board) {
        this.dictionary = dictionary;
        this.board = board;
        this.foundWords = new HashSet<>();
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

    private void DFS(BoggleDie die, Set<BoggleDie> visited, WordPath wordpath){
        visited.add(die);
        wordpath.addToPath(die);
        String word = wordpath.getWord();
        if (dictionary.isWord(word)) {
            if (!foundWords.contains(word)) {
                foundWords.add(word);
                System.out.println(wordpath);
            }
        }

        // something is buggy here that causes words to be missed
//        if (!dictionary.incompleteWord(word)) {
//            System.out.println("No words begin with " + word);
//            return;
//        }
        for (BoggleDie neighbor : die.getNeighbors()) {
            if (!visited.contains(neighbor)) {
                WordPath newWordPath = new WordPath(wordpath);
                DFS(neighbor, visited, newWordPath);
            }
        }
        visited.remove(die);

    }


}
