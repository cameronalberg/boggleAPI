package com.example.boggle;

import com.example.boggle.game.controller.BoggleSolver;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;

@RestController
public class TestController {

//    @RequestMapping(path="/", produces = "text/plain; charset=utf-8")
    public String getMessage() throws SQLException, IOException, ClassNotFoundException {
        return "Board Initialized!\n" + BoggleSolver.initBoard();
    }
}
