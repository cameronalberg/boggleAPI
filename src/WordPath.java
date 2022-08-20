import java.util.ArrayList;

public class WordPath {
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
        this.word += die.getActiveLetter();
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
}
