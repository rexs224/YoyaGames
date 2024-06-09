package com.example.app;

import android.content.Context;
import android.graphics.Color;


public class TableroDamas extends androidx.appcompat.widget.AppCompatImageView {
    public int row, col;
    PiezaDama pieza;

    public TableroDamas(Context context, int row, int col) {
        super(context);
        this.row = row;
        this.col = col;
        setBackgroundColor((row + col) % 2 == 0 ? Color.WHITE : Color.DKGRAY);
        setScaleType(ScaleType.CENTER_INSIDE);

    }

    public void setPiece(PiezaDama pieza) {
        this.pieza = pieza;
        if (pieza != null) {
            int resId = getPieceImageResource(pieza);
            setImageResource(resId);
        } else {
            setImageResource(0); // Borra la imagen
        }
    }

    private int getPieceImageResource(PiezaDama piece) {
        if (piece.isKing) {
            return piece.isWhite ? R.drawable.kingrojo : R.drawable.kingnegro;
        } else {
            return piece.isWhite ? R.drawable.damaroja : R.drawable.damanegra;
        }
    }

    public PiezaDama getPiece() {
        return pieza;
    }}
