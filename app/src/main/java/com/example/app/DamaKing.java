package com.example.app;

import com.example.app.PiezaDama;

public class DamaKing extends PiezaDama {
    public DamaKing(boolean isWhite, int row, int col) {
        super(isWhite, row, col);
        this.isKing = true;
    }

    public boolean isValidMove(int newRow, int newCol, PiezaDama[][] board) {
        if (board[newRow][newCol] != null) {
            return false; // Destination must be empty
        }

        int rowDiff = newRow - row;
        int colDiff = newCol - col;

        // Must move diagonally
        if (Math.abs(rowDiff) != Math.abs(colDiff)) {
            return false;
        }

        // Check if path is clear or if there's a valid capture
        int rowDirection = rowDiff / Math.abs(rowDiff);
        int colDirection = colDiff / Math.abs(colDiff);
        int steps = Math.abs(rowDiff);
        boolean hasCapture = false;

        for (int i = 1; i < steps; i++) {
            PiezaDama piece = board[row + i * rowDirection][col + i * colDirection];
            if (piece != null) {
                if (piece.isWhite != this.isWhite && !hasCapture) {
                    hasCapture = true; // Found an enemy piece to capture
                } else {
                    return false; // Path is blocked
                }
            }
        }

        // If move is more than one step, it must include a capture
        return steps == 1 || hasCapture;
    }
}