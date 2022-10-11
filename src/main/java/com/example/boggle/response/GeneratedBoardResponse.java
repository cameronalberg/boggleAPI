package com.example.boggle.response;

import io.swagger.v3.oas.annotations.media.Schema;

public class GeneratedBoardResponse {

    @Schema(example = "U-E-T-B")
    String board;

    public GeneratedBoardResponse(String board) {
        this.board = board;
    }

    public String getBoard() {
        return this.board;
    }

}
