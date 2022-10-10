package com.example.boggle;

import com.example.boggle.api.SolvedBoardResponse;
import com.example.boggle.game.board.BoggleBoard;
import com.example.boggle.game.data.TrieDictionary;
import com.example.boggle.game.solver.WordPath;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.BeansException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class BoggleAPIController implements BeanFactoryAware {
    private TrieDictionary dictionary;

    @Operation(summary= "Find all possible words in a provided board.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success!",
                content = { @Content(mediaType = "application/json",
                        schema = @Schema(implementation = SolvedBoardResponse.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid board.",
                content = @Content)})
    @GetMapping(path="/solve")
    @CrossOrigin
    public ResponseEntity<Object> solveBoard(@RequestParam @Parameter(name="inputBoard",
            description = "string representation of board characters",
            example="\"EILATPAGRETOHTAY\"") String inputBoard ) {

        if (BoggleBoard.validate(inputBoard) == null) {
            return new ResponseEntity<>("{\"error\": \"invalid board\"}",
                    HttpStatus.BAD_REQUEST);
        }

        BoggleBoard board = new BoggleBoard(inputBoard);
        SolvedBoardResponse results = board.getSolution(dictionary);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @Operation(summary= "Get a new board.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returned a valid board.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters.",
                    content = @Content)})
    @GetMapping(path="/shuffle")
    @CrossOrigin
    public ResponseEntity<Object> shuffleBoard(@RequestParam(required = false) @Parameter(name=
            "boardSize",
            description = "board dimension size",
            example="4") Integer boardSize) {
        if (boardSize == null) {
            boardSize = 4;
        }
        if (boardSize < 3 || boardSize > 6) {
            return new ResponseEntity<>("{\"error\": \"board size must be " +
                    "between 3 and 6\"}",
                    HttpStatus.BAD_REQUEST);
        }
        BoggleBoard board = new BoggleBoard(boardSize);
        String response = "{\"board\": \"" + board + "\"}";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public void setBeanFactory(BeanFactory context) throws BeansException {
        dictionary = (TrieDictionary) context.getBean("getDictionary");
    }

}
