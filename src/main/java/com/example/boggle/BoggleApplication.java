package com.example.boggle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.sql.SQLException;

@SpringBootApplication
public class BoggleApplication {

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        SpringApplication.run(BoggleApplication.class, args);

    }

}
