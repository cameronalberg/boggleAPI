package com.example.boggle.game.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.sql.*;
import java.util.Scanner;

@Component("dictionary")
public class BoggleDictionary {

    private final TrieDictionary dictionary;
    private String database;

    public BoggleDictionary() {
        this.dictionary = new TrieDictionary();
    }

    @Bean
    public TrieDictionary getDictionary() {
        return this.dictionary;
    }

    @Autowired
    private void populateDictionary(@Value("${database}") String database){
        if (database == null || database.equals("default")) {
            database = "dictionary.txt";
            System.out.println("no database set, using default");
        }
        String tempDatabase = getPath(database);
        File tempFile = new File(tempDatabase);
        if (tempFile.exists() && !tempFile.isDirectory()) {
            database = tempDatabase;
        }
        else {
            System.out.println("could not find database " + tempDatabase +
                    ", using backup");
            database = "./backup/dictionary.txt";
        }
        try {
                File file = new File(database);
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String word = scanner.nextLine();
                    this.dictionary.addWord(word);
            }
            System.out.println("loaded dictionary, number of words: "
                    + dictionary.wordCount());
        } catch (Exception s) {
            System.out.println(s.getMessage());
        }
    }

    private static String getPath(String input) {
        return "./data/" + input;
    }

    private static Connection loadDictionary(String connectionUrl) throws ClassNotFoundException {

        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite" +
                    ":" + connectionUrl);
            System.out.println("database loaded");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
