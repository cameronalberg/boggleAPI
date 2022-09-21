package com.example.boggle;

import com.example.boggle.api.SolvedBoardResponse;
import com.example.boggle.game.board.BoggleBoard;
import com.example.boggle.game.data.TrieDictionary;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class BoggleAPIController implements ErrorController, BeanFactoryAware {
    private TrieDictionary dictionary;

    @GetMapping(path="/solve")
    @CrossOrigin
    public ResponseEntity<Object> solveBoard(HttpServletRequest request) {
        String inputBoard = request.getParameter("board");

        if (BoggleBoard.validate(inputBoard) == null) {
            return new ResponseEntity<>("{\"error\": \"invalid board\"}",
                    HttpStatus.BAD_REQUEST);
        }

        BoggleBoard board = new BoggleBoard(inputBoard);
        SolvedBoardResponse results = board.getSolution(dictionary);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping(path="/shuffle")
    @CrossOrigin
    public ResponseEntity<Object> shuffleBoard(HttpServletRequest request) {
        String sizeString = request.getParameter("boardSize");
        int size = 4;
        try {
            if (sizeString != null) {
                size = Integer.parseInt(sizeString);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("{\"error\": board size could " +
                    "not be converted to integer}",
                    HttpStatus.BAD_REQUEST);
        }
        if (size < 3 || size > 6) {
            return new ResponseEntity<>("{\"error\": board size must be " +
                    "between 3 and 6}",
                    HttpStatus.BAD_REQUEST);
        }
        BoggleBoard board = new BoggleBoard(size);
        String response = "{\"board\": \"" + board + "\"}";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public void setBeanFactory(BeanFactory context) throws BeansException {
        dictionary = (TrieDictionary) context.getBean("getDictionary");
    }
}
