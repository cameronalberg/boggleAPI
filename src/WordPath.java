import java.util.ArrayList;
import java.util.Objects;

public class WordPath implements Comparable<WordPath>{
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
    public int compareTo(WordPath o) {
        String current = this.getWord();
        String compared = o.getWord();
        return current.compareTo(compared);
    }
}
