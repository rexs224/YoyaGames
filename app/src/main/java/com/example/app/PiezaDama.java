package com.example.app;

public abstract class PiezaDama {
    public boolean isWhite;
    public boolean isKing;
    public int row, col;

    public PiezaDama(boolean isWhite, int row, int col) {
        this.isWhite = isWhite;
        this.isKing = false ;
        this.row = row;
        this.col = col;
    }
    public abstract boolean isValidMove(int newRow, int newCol, PiezaDama[][] board);

    public void promoteToKing() {
        this.isKing = true;
    }
}

