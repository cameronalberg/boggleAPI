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

    public BoggleDictionary() {
        this.dictionary = new TrieDictionary();
    }

    @Bean
    public TrieDictionary getDictionary() {
        return this.dictionary;
    }

    public boolean isWord(String word) {
        return this.dictionary.isWord(word);
    }

    public boolean incompleteWord(String word) {
        return this.dictionary.incompleteWord(word);
    }

    @Autowired
    public void populateDictionary(@Value("${database}") String database,
                                    @Value("${database.defaultPath}") String path){
        final String DICT_DEFAULT = "dictionary.txt";
        final String PATH_DEFAULT = "src/main/resources/data";
        final String CUST_PATH_DEFAULT = "/data";

        if (database == null || database.isEmpty() || database.equals("default")) {
            database = DICT_DEFAULT;
            path = PATH_DEFAULT;
            System.out.println("no database set, using default");
        } else if (path == null || path.isEmpty() || path.equals("default")) {
            path = CUST_PATH_DEFAULT;
        }
        String databasePath = getPath(database, path);
        if (!readFile(databasePath, dictionary)) {
            System.out.println("Using backup dictionary");
            readFile(getPath(DICT_DEFAULT, PATH_DEFAULT), dictionary);
        }
        System.out.println("Loaded dictionary, number of words: " + dictionary.wordCount());
    }

    private static boolean readFile(String filePath,
                                    TrieDictionary dictionary) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine();
                dictionary.addWord(word);
            }
        } catch (Exception e) {
            dictionary.clear();
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    private static String getPath(String input, String path) {
        return "./" + path + "/" + input;
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
