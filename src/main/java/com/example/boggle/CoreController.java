package com.example.boggle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.net.URI;
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

    @RequestMapping(path="/demo")
    public ResponseEntity<Object> redirect() {
        return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT).location(URI.create(
                "https://boggle-demo.calberg.me")).build();
    }

    @RequestMapping(path="/error")
    public String displayError() {
        return "404.html";
    }

}
