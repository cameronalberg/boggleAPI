package com.example.boggle.game.data;

import com.example.boggle.game.controller.BoggleSolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

@Component("dictionary")
public class BoggleDictionary {

    private final TrieDictionary dictionary;

    public BoggleDictionary() {
        this.dictionary = new TrieDictionary();
        populateDictionary("\\data\\dictionary_scrabble" +
                ".txt");
    }

    @Bean
    public TrieDictionary getDictionary() {
        return this.dictionary;
    }

    private TrieDictionary populateDictionary(String databaseName){
        try {
            String database = getPath(databaseName);
            if (database.contains("db")) {
                Connection con = loadDictionary(database);
                if (con.isClosed()) {
                    System.out.println("database was closed?");
                }
                Statement stmt = con.createStatement();
                String query = "SELECT word FROM words";
                ResultSet resultSet = stmt.executeQuery(query);
                while (resultSet.next()) {
                    this.dictionary.addWord(resultSet.getString(1));
                }
                con.close();
            } else {
                File file = new File(database);
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String word = scanner.nextLine();
                    this.dictionary.addWord(word);
                }
            }
        } catch (Exception s) {
            System.out.println(s.getMessage());
        }


        return dictionary;
    }

    private static String getPath(String input) throws IOException {
        String currentPath = System.getProperty("user.dir");
        return currentPath + input;

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
