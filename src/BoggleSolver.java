import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class BoggleSolver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        int boardSize = 4;
//        String filename = getPath(args[0]);
        String database = "\\data\\dictionary_big.db";
//        String input = retrieveBoard(filename);
        BoggleBoard board = new BoggleBoard(boardSize);
        System.out.println(board);
        TrieDictionary dictionary = populateDictionary(database);
        int words = dictionary.wordCount();
        System.out.println("Number of words in dictionary: " + words);

        BoggleTraversal searcher = new BoggleTraversal(dictionary, board);
        searcher.traverse();
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
        return dictionary;
    }

}
