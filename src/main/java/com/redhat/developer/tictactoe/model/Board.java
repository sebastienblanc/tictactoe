package com.redhat.developer.tictactoe.model;

import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Board extends PanacheEntity{

    //0 is free, 1 is circle and 2 is cross
    private Integer[][] grid = new Integer[3][3];

    public Integer[][] getGrid() {
        return grid;
    }

    public void setGrid(Integer[][] grid) {
        this.grid = grid;
    }

    public void setMark(int row, int column, int type) {
        grid[row][column] = type;
    }
    
}