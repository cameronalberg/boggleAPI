package com.example.boggle.game.data;

public interface Dictionary {
    // Returns True if passed string is a valid word
    boolean isWord(String word);

    // Returns False if no words can be completed using the passed string (as
    // a prefix)
    boolean incompleteWord(String word);

    void addWord(String word);

}
