package com.redhat.developer.tictactoe.dto;

import com.redhat.developer.tictactoe.model.Board;

public class BoardDTO implements ServerSideEventMessage { 

    private Integer[][] grid = new Integer[3][3];

    public Integer[][] getGrid() {
        return grid;
    }

    public void setGrid(Integer[][] grid) {
        this.grid = grid;
    }

    public static BoardDTO of(Board board) {
        final BoardDTO boardDTO = new BoardDTO();
        boardDTO.setGrid(board.getGrid());
        return boardDTO;
    }

}
    
