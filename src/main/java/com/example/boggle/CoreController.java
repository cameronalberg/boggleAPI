package com.example.boggle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.sql.SQLException;

@SpringBootApplication
@Controller
public class CoreController {

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        SpringApplication.run(CoreController.class, args);

    }

    @RequestMapping(path="/")
    public String root() {
        return "index.html";
    }

    @RequestMapping(path="/error")
    public String displayError() {
        return "404.html";
    }

}
