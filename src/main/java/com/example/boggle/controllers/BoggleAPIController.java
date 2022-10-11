package com.example.boggle.controllers;

import com.example.boggle.response.ApiError;
import com.example.boggle.response.GeneratedBoardResponse;
import com.example.boggle.response.SolvedBoardResponse;
import com.example.boggle.game.board.BoggleBoard;
import com.example.boggle.game.data.TrieDictionary;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BoggleAPIController implements BeanFactoryAware {
    private TrieDictionary dictionary;

    @Operation(summary = "Find all possible words in a provided board.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search results returned.",
                    content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = SolvedBoardResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid board.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))})})
    @GetMapping(path = "/solve")
    @CrossOrigin
    public ResponseEntity<Object> solveBoard(
            @RequestParam @Parameter(name = "board",
                    description = "string representation of board characters",
                    example = "\"UETB\"") String board
    ) {

        if (BoggleBoard.validate(board) == null) {
            ApiError error = new ApiError("invalid board");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        BoggleBoard boggleBoard = new BoggleBoard(board);
        SolvedBoardResponse response = boggleBoard.getSolution(dictionary);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get a new board.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returned a valid board.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid board.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))})})
    @GetMapping(path = "/shuffle")
    @CrossOrigin
    public ResponseEntity<Object> shuffleBoard(
            @RequestParam(required = false)
            @Parameter(name = "size",
                    description = "board dimension size",
                    example = "4") Integer size) {

        if (size == null) {
            size = 4;
        }
        if (size < 3 || size > 6) {
            ApiError error = new ApiError("size must be integer between 3 and 6");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        BoggleBoard board = new BoggleBoard(size);
        GeneratedBoardResponse response = new GeneratedBoardResponse(board.toString());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public void setBeanFactory(BeanFactory context) throws BeansException {
        dictionary = (TrieDictionary) context.getBean("getDictionary");
    }

}
