package com.example.boggle.game.data;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.sql.*;
import java.util.Scanner;

@Component("dictionary")
public class BoggleDictionary {

    private final TrieDictionary dictionary;

    public BoggleDictionary() {
        this.dictionary = new TrieDictionary();
        populateDictionary();
    }

    @Bean
    public TrieDictionary getDictionary() {
        return this.dictionary;
    }

    private void populateDictionary(){
        String databaseName = System.getenv("database");
        String database = "./data/dictionary.txt";
        if (databaseName == null) {
            databaseName = "dictionary.txt";
            System.out.println("no database set, using default: "
                                + databaseName);
        }
        else {
            String tempDatabase = getPath(databaseName);
            File file = new File(tempDatabase);
            if (file.exists() && !file.isDirectory()) {
                database = tempDatabase;
            }
            else {
                System.out.println("could not find database " + tempDatabase +
                        ", using default: "+ databaseName);
            }
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
