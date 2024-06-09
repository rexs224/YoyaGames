package com.example.app;

public class DamaNormal extends PiezaDama {
    public DamaNormal(boolean isWhite, int row, int col) {
        super(isWhite, row, col);
    }

    @Override
    public boolean isValidMove(int newRow, int newCol, PiezaDama[][] board) {
        // Cannot move to an occupied square
        if (board[newRow][newCol] != null) {
            return false;
        }

        int rowDiff = newRow - row;
        int colDiff = newCol - col;

        // Move forward diagonally (1 square) or capture (2 squares)
        if (Math.abs(colDiff) == 1 && ((isWhite && rowDiff == 1) || (!isWhite && rowDiff == -1))) {
            return true;
        } else if (Math.abs(colDiff) == 2 && ((isWhite && rowDiff == 2) || (!isWhite && rowDiff == -2))) {
            int middleRow = (row + newRow) / 2;
            int middleCol = (col + newCol) / 2;
            if (board[middleRow][middleCol] != null && board[middleRow][middleCol].isWhite != isWhite) {
                return true;
            }
        }

        return false;
    }
}
