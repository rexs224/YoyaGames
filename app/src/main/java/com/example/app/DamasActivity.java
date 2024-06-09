package com.example.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.ComponentActivity;

public class DamasActivity extends ComponentActivity {

    private TableroDamas[][] squares = new TableroDamas[8][8];
    private PiezaDama[][] board = new PiezaDama[8][8];
    private  PiezaDama piezaSeleccionada= null;
    private boolean isWhiteTurn = true;
    TextView cuentaAtras;
    TextView texto1;
    ImageView fondoOscuro;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_damas);

        cuentaAtras = findViewById(R.id.cuentaAtras);
        texto1 = findViewById(R.id.tww2);
        fondoOscuro = findViewById(R.id.fondooscuro);

        new CountDownTimer(4000, 1000) {
            public void onTick(long millisUntilFinished) {
                cuentaAtras.setText(Integer.toString(Integer.parseInt(cuentaAtras.getText().toString()) - 1));
            }

            public void onFinish() {
                empezar();
            }
        }.start();
    }

    private void empezar(){
        texto1.setVisibility(View.INVISIBLE);
        cuentaAtras.setVisibility(View.INVISIBLE);
        fondoOscuro.setVisibility(View.INVISIBLE);

        GridLayout checkersBoard = findViewById(R.id.checkersBoard);
        checkersBoard.setRowCount(8);
        checkersBoard.setColumnCount(8);

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                TableroDamas square = new TableroDamas(this, row, col);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.rowSpec = GridLayout.spec(row);
                params.columnSpec = GridLayout.spec(col);
                params.width = 120;
                params.height = 120;
                square.setLayoutParams(params);
                square.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleSquareClick(square);
                    }
                });
                checkersBoard.addView(square);
                squares[row][col] = square;
            }
        }
        initializePieces();
        updateBoard();
    }



    private void initializePieces() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 != 0) {
                    board[row][col] = new DamaNormal(true, row, col);
                }
            }
        }
        for (int row = 5; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 != 0) {
                    board[row][col] = new DamaNormal(false, row, col);
                }
            }
        }
    }

    private void updateBoard() {

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                squares[row][col].setPiece(board[row][col]);
            }
        }
    }

    private void handleSquareClick(TableroDamas square) {
        int row = square.row;
        int col = square.col;

        if (piezaSeleccionada == null) {
            if (board[row][col] != null) {
                piezaSeleccionada = board[row][col];
            }
        } else {
            if (piezaSeleccionada.isValidMove(row, col, board)) {
                    if (Math.abs(piezaSeleccionada.row - row) == 2 && Math.abs(piezaSeleccionada.col - col) == 2) {
                    int middleRow = (piezaSeleccionada.row + row) / 2;
                    int middleCol = (piezaSeleccionada.col + col) / 2;
                    board[middleRow][middleCol] = null; // Remove the captured piece
                }
                // Move the piece
                board[piezaSeleccionada.row][piezaSeleccionada.col] = null;
                piezaSeleccionada.row = row;
                piezaSeleccionada.col = col;
                board[row][col] = piezaSeleccionada;

                // Handle promotion to king
                if ((piezaSeleccionada.isWhite && row == 7) || (!piezaSeleccionada.isWhite && row == 0)) {
                    piezaSeleccionada.promoteToKing();
                    board[row][col] = new DamaKing(piezaSeleccionada.isWhite, row, col);
                }

                piezaSeleccionada = null;
                updateBoard();

                checkForWin();
                isWhiteTurn = !isWhiteTurn;

            } else {
                piezaSeleccionada = null; // Deselect if move is invalid
            }
        }
    }

    private void checkForWin() {
        boolean whiteExists = false;
        boolean blackExists = false;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] != null) {
                    if (board[row][col].isWhite) {
                        whiteExists = true;
                    } else {
                        blackExists = true;
                    }
                }
            }
        }

        if (!whiteExists) {
            AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
            dialogo.setTitle("¡Ha ganado el Jugador negro!");
            dialogo.setMessage("¿Quereis jugar de nuevo?");
            dialogo.setCancelable(false);
            dialogo.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    empezar();
                }
            });
            dialogo.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    Intent intent = new Intent(DamasActivity.this, VistaPrincipal.class);
                    startActivity(intent);
                    finish();
                    empezar();
                }
            });
            dialogo.show();
        } else if (!blackExists) {
            AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
            dialogo.setTitle("¡Ha ganado el Jugador rojo!");
            dialogo.setMessage("¿Quereis jugar de nuevo?");
            dialogo.setCancelable(false);
            dialogo.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    empezar();
                }
            });
            dialogo.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    Intent intent = new Intent(DamasActivity.this, VistaPrincipal.class);
                    startActivity(intent);
                    finish();
                    empezar();
                }
            });
            dialogo.show();
        }
    }
}
