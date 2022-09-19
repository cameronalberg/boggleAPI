package com.example.boggle.controller;

import com.example.boggle.game.board.BoggleBoard;
import com.example.boggle.game.data.TrieDictionary;
import com.example.boggle.game.solver.WordPath;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class GameController implements ErrorController, BeanFactoryAware {
    private TrieDictionary dictionary;

    @RequestMapping(path="/")
    public String root() {
        return "home";
    }

//    @GetMapping(path="/solve")
//    public SolvedBoardResponse solveBoard(HttpServletRequest request) {
//        String inputBoard = request.getParameter("board");
//
//        //TODO - take input board as arg for board creation
//        BoggleBoard board = new BoggleBoard(4);
//        board.search(dictionary);
//
//        // Put found words into JSON response format
//        return board.getSolution();
//    }
    @GetMapping(path="/solve")
    public ResponseEntity<Object> solveBoard(HttpServletRequest request) {
        String inputBoard = request.getParameter("board");
        String validatedBoard = BoggleBoard.validate(inputBoard);
        if (validatedBoard == null) {
            return new ResponseEntity<>("invalid board",
                    HttpStatus.BAD_REQUEST);
        }
        BoggleBoard board = new BoggleBoard(inputBoard);
        board.search(dictionary);

        // Put found words into JSON response format
        SolvedBoardResponse results = board.getSolution();
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @RequestMapping(path="/error")
    public String displayError() throws SQLException, IOException,
            ClassNotFoundException {
        return "404 Not Found";
    }

    @Override
    public void setBeanFactory(BeanFactory context) throws BeansException {
        dictionary = (TrieDictionary) context.getBean("getDictionary");
    }
}
