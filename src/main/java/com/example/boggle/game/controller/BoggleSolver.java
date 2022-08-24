package com.example.boggle.game.controller;

import com.example.boggle.game.board.BoggleBoard;
import com.example.boggle.game.data.TrieDictionary;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class BoggleSolver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        String dictionaryFile;
        int boardSize;
        TrieDictionary dictionary;

        // Load dictionary file
        try {
            dictionaryFile = args[0];
            dictionary = populateDictionary(dictionaryFile);
            System.out.println("Loaded provided dictionary: " + dictionaryFile);
        } catch (Exception e) {
            System.out.println("Using default dictionary");
            dictionaryFile = "\\data\\dictionary_scrabble.txt";
            dictionary = populateDictionary(dictionaryFile);
        }
        int words = dictionary.wordCount();
        System.out.println("Number of words in dictionary: " + words);

        //Get Board Size
        try {
            boardSize = Integer.parseInt(args[1]);
            System.out.println("Generating " + boardSize + " x " + boardSize +
                    " board");
        } catch (Exception e) {
            System.out.println("Using default board (4x4)");
            boardSize = 4;
        }

        BoggleBoard board = new BoggleBoard(boardSize);
        board.printBoard();

        //Find all words and time results


        board.search(dictionary);

        board.printStats();

        board.printWords();

    }

    public static String initBoard() throws IOException,
            ClassNotFoundException, SQLException {
        String dictionaryFile;
        int boardSize;
        TrieDictionary dictionary;

        // Load dictionary file
            dictionaryFile = "\\data\\dictionary_scrabble.txt";
            dictionary = populateDictionary(dictionaryFile);
        int words = dictionary.wordCount();

        //Get Board Size
            boardSize = 4;

        BoggleBoard board = new BoggleBoard(boardSize);
        board.shuffleBoard();
        return board.toString();
    }

    public static String getPath(String input) throws IOException {
        String currentPath = new java.io.File(".").getCanonicalPath();
        return currentPath + input;

    }

    public static String retrieveBoard(String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner sc = new Scanner(file);
        StringBuilder input = new StringBuilder();
        while (sc.hasNextLine())
            input.append(sc.nextLine());
        return input.toString();
    }

    public static Connection loadDictionary(String connectionUrl) throws ClassNotFoundException {

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

    public static TrieDictionary populateDictionary(String databaseName) throws IOException, ClassNotFoundException, SQLException {
        TrieDictionary dictionary = new TrieDictionary();
        String database = getPath(databaseName);
        if (database.contains("db")) {
            Connection con = BoggleSolver.loadDictionary(database);
            if (con.isClosed()) {
                System.out.println("database was closed?");
            }
            Statement stmt = con.createStatement();
            String query = "SELECT word FROM words";
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                dictionary.addWord(resultSet.getString(1));
            }
            con.close();
        } else {
            File file = new File(database);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine();
                dictionary.addWord(word);
            }
        }

        return dictionary;
    }

}
