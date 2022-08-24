package com.example.boggle.game.data;

import java.util.HashMap;

public class TrieNode {
    private HashMap<Character, TrieNode> children;
    private String value;
    private boolean isWord;

    public TrieNode() {
        this.value = null;
        this.isWord = false;
        this.children = new HashMap<Character, TrieNode>();
    }

    public TrieNode getNode() {
        return this;
    }

    public void addChild(char letter, TrieNode node) {
        this.children.putIfAbsent(letter, node);
    }

    public void setAsValidWord() {
        this.isWord = true;
    }

    public TrieNode get(char letter) {
        return this.children.getOrDefault(letter, null);
    }

    public boolean isWord() {
        return this.isWord;
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }
}
