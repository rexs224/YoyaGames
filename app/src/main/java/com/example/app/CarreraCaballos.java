package com.example.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.ComponentActivity;

public class CarreraCaballos extends ComponentActivity {
    Button botonj1;
    Button botonj2;
    TextView txj1;
    TextView txj2;
    static ImageView imgcaballo1;
    static ImageView imgcaballo2;
    static int contadorCaballo1 = 0;
    static int contadorCaballo2 = 0;
    TextView cuentaAtras;
    TextView texto1;
    ImageView fondoOscuro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.carrerascaballos);
        imgcaballo1 = findViewById(R.id.imgcaballo1);
        imgcaballo2 = findViewById(R.id.imgcaballo2);
        botonj1 = findViewById(R.id.j1);
        botonj2 = findViewById(R.id.j2);
        txj1 = findViewById(R.id.textj1);
        txj2 = findViewById(R.id.textj2);

        fondoOscuro = findViewById(R.id.fondooscuro);
        cuentaAtras = findViewById(R.id.cuentaAtras);
        texto1 = findViewById(R.id.tww);

        botonj1.setClickable(false);
        botonj2.setClickable(false);
        new CountDownTimer(4000, 1000) {
            public void onTick(long millisUntilFinished) {
                cuentaAtras.setText(Integer.toString(Integer.parseInt(cuentaAtras.getText().toString()) - 1));
            }

            public void onFinish() {
                fondoOscuro.setVisibility(View.INVISIBLE);
                cuentaAtras.setVisibility(View.INVISIBLE);
                texto1.setVisibility(View.INVISIBLE);

                botonj1.setClickable(true);
                botonj2.setClickable(true);
            }
        }.start();
    }

    public void moverj1(View view) {

        if (contadorCaballo1 <= 15) {
            moverCaballo(40, 4, imgcaballo1, 1);
        } else if (contadorCaballo1 > 15 && contadorCaballo1 <= 32) {
            moverCaballo(35, 10, imgcaballo1, 1);
        } else if (contadorCaballo1 == 32) {
            imgcaballo1.setScaleX(-1);
        } else if (contadorCaballo1 > 32 && contadorCaballo1 <= 46) {
            moverCaballo(2, 17, imgcaballo1, 1);
        } else if (contadorCaballo1 > 46 && contadorCaballo1 <= 52) {
            moverCaballo(-35, 7, imgcaballo1, 1);
        }

        verificarganador();
    }

    public void moverj2(View view) {
        if (contadorCaballo2 <= 15) {
            moverCaballo(50, 4, imgcaballo2, 2);
        } else if (contadorCaballo2 > 15 && contadorCaballo2 <= 32) {
            moverCaballo(35, 20, imgcaballo2, 2);
        } else if (contadorCaballo2 == 32) {
            imgcaballo2.setScaleX(-1);

        } else if (contadorCaballo2 > 32 && contadorCaballo2 <= 46) {
            moverCaballo(2, 17, imgcaballo2, 2);
        } else if (contadorCaballo2 > 46 && contadorCaballo2 <= 53) {
            moverCaballo(-35, 7, imgcaballo2, 2);
        }
        verificarganador();
    }

    private void verificarganador() {
        if (contadorCaballo1 == 52) {

            pregunta(1);

        } else if (contadorCaballo2 == 52) {

            pregunta(2);
        }
    }

    private void moverCaballo(int leftMargin, int topMargin, ImageView caballo, int numCaballo) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) caballo.getLayoutParams();
        params.setMargins(params.leftMargin + leftMargin, params.topMargin + topMargin, params.rightMargin, params.bottomMargin);
        caballo.setLayoutParams(params);
        if (numCaballo == 1) {
            contadorCaballo1++;
        } else if (numCaballo == 2) {
            contadorCaballo2++;
        }
    }

    private void resetearPosiciones() {
        contadorCaballo1 = 0;
        contadorCaballo2 = 0;

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) imgcaballo1.getLayoutParams();
        params.setMargins(0, 0, 0, 0);
        imgcaballo1.setLayoutParams(params);

        ViewGroup.MarginLayoutParams params2 = (ViewGroup.MarginLayoutParams) imgcaballo2.getLayoutParams();
        params2.setMargins(0, 0, 0, 0);
        imgcaballo2.setLayoutParams(params2);

        botonj1.setClickable(false);
        botonj2.setClickable(false);
        fondoOscuro.setVisibility(View.VISIBLE);
        texto1.setVisibility(View.VISIBLE);
        cuentaAtras.setText("4");
        cuentaAtras.setVisibility(View.VISIBLE);

        new CountDownTimer(4000, 1000) {
            public void onTick(long millisUntilFinished) {
                cuentaAtras.setText(Integer.toString(Integer.parseInt(cuentaAtras.getText().toString()) - 1));
            }

            public void onFinish() {
                fondoOscuro.setVisibility(View.INVISIBLE);
                cuentaAtras.setVisibility(View.INVISIBLE);
                texto1.setVisibility(View.INVISIBLE);

                botonj1.setClickable(true);
                botonj2.setClickable(true);
            }
        }.start();

    }

    private void pregunta(int numCaballo) {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("¡Ha ganado el J" + numCaballo + "!");
        dialogo.setMessage("¿Quereis jugar de nuevo?");
        dialogo.setCancelable(false);
        dialogo.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                resetearPosiciones();
                if (numCaballo == 1)
                    txj1.setText(Integer.toString(1 + Integer.parseInt(txj1.getText().toString())));
                if (numCaballo == 2)
                    txj2.setText(Integer.toString(1 + Integer.parseInt(txj2.getText().toString())));
            }
        });
        dialogo.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                Intent intent = new Intent(CarreraCaballos.this, VistaPrincipal.class);
                startActivity(intent);
                finish();
                resetearPosiciones();
            }
        });
        dialogo.show();
    }
}