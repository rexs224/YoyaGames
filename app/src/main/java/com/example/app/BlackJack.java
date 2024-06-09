package com.example.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;


public class BlackJack extends ComponentActivity {
    ImageView plantarse1;
    ImageView plantarse2;
    ImageView fondoOscuro;
    ImageView cogerCarta;
    TextView valorTotal1;
    TextView valorTotal2;
    TextView valorTotalCrupier;
    TextView cuentaAtras;
    TextView texto1;
    static boolean turnoJ1 = true;
    static int suma1, suma2, suma3;
    static ArrayList<String> manoJ1 = new ArrayList<>();
    static ArrayList<String> manoJ2 = new ArrayList<>();
    static ArrayList<String> manoCrupier = new ArrayList<>();
    static int posicionManoJ1 = 2;
    static int posicionManoJ2 = 2;
    static int posicionManoCrupier = 1;
    static ArrayList<ImageView> listaImagesJ1 = new ArrayList<>();
    static ArrayList<ImageView> listaImagesJ2 = new ArrayList<>();
    static ArrayList<ImageView> listaImagesCrupier = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blackjack);

        cuentaAtras = findViewById(R.id.cuentaAtras);
        texto1 = findViewById(R.id.tww);

        valorTotal1 = findViewById(R.id.valorTotal1);
        valorTotal2 = findViewById(R.id.valorTotal2);
        valorTotalCrupier = findViewById(R.id.valorTotalCrupier);

        int[] j1Cartas = {R.id.card1j1, R.id.card2j1, R.id.card3j1, R.id.card4j1, R.id.card5j1};
        for (int id : j1Cartas) {
            ImageView carta = findViewById(id);
            listaImagesJ1.add(carta);
        }
        int[] j2Cartas = {R.id.card1j2, R.id.card2j2, R.id.card3j2, R.id.card4j2, R.id.card5j2};
        for (int id : j2Cartas) {
            ImageView carta = findViewById(id);
            listaImagesJ2.add(carta);
        }

        int[] crupierCartas = {R.id.cardCrupier1, R.id.cardCrupier2, R.id.cardCrupier3, R.id.cardCrupier4, R.id.cardCrupier5, R.id.cardCrupier6, R.id.cardCrupier7};
        for (int i = 0; i < crupierCartas.length; i++) {
            ImageView img = findViewById(crupierCartas[i]);
            listaImagesCrupier.add(img);
        }

        cogerCarta = findViewById(R.id.cogerCarta);

        plantarse1 = findViewById(R.id.plantarseJ1);
        plantarse2 = findViewById(R.id.plantarseJ2);

        fondoOscuro = findViewById(R.id.fondooscuro);


        plantarse1.setClickable(false);
        plantarse2.setClickable(false);
        cogerCarta.setClickable(false);

        new CountDownTimer(4000, 1000) {
            public void onTick(long millisUntilFinished) {
                cuentaAtras.setText(Integer.toString(Integer.parseInt(cuentaAtras.getText().toString()) - 1));
            }

            public void onFinish() {
                empezarPartida();
            }
        }.start();
    }

    private void empezarPartida() {
        texto1.setVisibility(View.INVISIBLE);
        cuentaAtras.setVisibility(View.INVISIBLE);
        fondoOscuro.setVisibility(View.INVISIBLE);

        Toast.makeText(getApplicationContext(), "Reparto de cartas", Toast.LENGTH_SHORT).show();

        ArrayList<String> baraja = new ArrayList<>();
        String[] palos = {"D", "H", "C", "S"};
        String[] numeros = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "0", "J", "Q", "K"};

        for (String palo : palos) {
            for (String numero : numeros) {
                if (!(palo.equals("D") && numero.equals("A"))) {
                    baraja.add(numero + palo);
                }
            }
        }
        baraja.add("aceDiamonds");
        Collections.shuffle(baraja);

        for (int i = 0; i < 5; i++) {
            manoJ1.add(baraja.get(i));
        }
        for (int i = 5; i < 10; i++) {
            manoJ2.add(baraja.get(i));
        }
        for (int i = 10; i < 17; i++) {
            manoCrupier.add(baraja.get(i));
        }

        //ESTABLECER PUNTUACION Y BARAJA INICIAL JUGADOR 1
        String imageUrl11 = "https://deckofcardsapi.com/static/img/" + manoJ1.get(0) + ".png";
        Picasso.get().load(imageUrl11).into(listaImagesJ1.get(0));
        String imageUrl21 = "https://deckofcardsapi.com/static/img/" + manoJ1.get(1) + ".png";
        Picasso.get().load(imageUrl21).into(listaImagesJ1.get(1));
        int num1 = devolverValorCarta(manoJ1.get(0));
        int num2 = devolverValorCarta(manoJ1.get(1));
        suma1 = num1 + num2;
        valorTotal1.setText(Integer.toString(suma1));


        //ESTABLECER PUNTUACION Y BARAJA INICIAL JUGADOR 2
        String imageUrl12 = "https://deckofcardsapi.com/static/img/" + manoJ2.get(0) + ".png";
        Picasso.get().load(imageUrl12).into(listaImagesJ2.get(0));
        String imageUrl22 = "https://deckofcardsapi.com/static/img/" + manoJ2.get(1) + ".png";
        Picasso.get().load(imageUrl22).into(listaImagesJ2.get(1));
        int num3 = devolverValorCarta(manoJ2.get(0));
        int num4 = devolverValorCarta(manoJ2.get(1));
        suma2 = num3 + num4;
        valorTotal2.setText(Integer.toString(suma2));


        //ESTABLECER PUNTUACION Y BARAJA INICIAL CRUPIER
        String imageCrupier1 = "https://deckofcardsapi.com/static/img/" + manoCrupier.get(0) + ".png";
        Picasso.get().load(imageCrupier1).into(listaImagesCrupier.get(0));
        suma3 = devolverValorCarta(manoCrupier.get(0));
        valorTotalCrupier.setText(Integer.toString(suma3));


        new Handler().postDelayed(() -> {
            if (!validarGanador2()) {
                Toast.makeText(getApplicationContext(), "Turno Jugador 1", Toast.LENGTH_LONG).show();
                cogerCarta.setVisibility(View.VISIBLE);
                cogerCarta.setClickable(true);
                plantarse1.setClickable(true);
            }

        }, 3000);
    }

    public void turno(View view) {
        if (turnoJ1) {
            vibrar();
            String imageNueva = "https://deckofcardsapi.com/static/img/" + manoJ1.get(posicionManoJ1) + ".png";
            Picasso.get().load(imageNueva).into(listaImagesJ1.get(posicionManoJ1));

            listaImagesJ1.get(posicionManoJ1).setVisibility(View.VISIBLE);

            suma1 = verificarValorAs(manoJ1, posicionManoJ1, valorTotal1);

            if (suma1 > 21) {
                Toast.makeText(getApplicationContext(), "Perdiste! Turno Jugador 2", Toast.LENGTH_SHORT).show();
                plantarse1.setClickable(false);
                valorTotal1.setTextColor(Color.RED);
                turnoJ1 = false;
                plantarse2.setClickable(true);
            }
            if (suma1 == 21) {
                validarGanador();
                valorTotal1.setTextColor(Color.YELLOW);
            }
            posicionManoJ1++;

        } else {
            vibrar();
            String imageNueva = "https://deckofcardsapi.com/static/img/" + manoJ2.get(posicionManoJ2) + ".png";
            Picasso.get().load(imageNueva).into(listaImagesJ2.get(posicionManoJ2));
            listaImagesJ2.get(posicionManoJ2).setVisibility(View.VISIBLE);

            suma2 = verificarValorAs(manoJ2, posicionManoJ2, valorTotal2);
            if (suma2 > 21) {
                Toast.makeText(getApplicationContext(), "Perdiste! Turno Crupier", Toast.LENGTH_SHORT).show();
                plantarse2.setClickable(false);
                valorTotal2.setTextColor(Color.RED);

                new Handler().postDelayed(() -> {
                    turnoCrupier();
                }, 3000);
            }
            if (suma2 == 21) {
                validarGanador();
                valorTotal2.setTextColor(Color.YELLOW);
            }
            posicionManoJ2++;
        }
    }

    private int devolverValorCarta(String carta) {
        int num;
        switch (carta.charAt(0)) {
            case 'J':
            case 'Q':
            case 'K':
            case '0':
                num = 10;
                break;
            case 'A':
            case 'a':
                num = 11;
                break;
            default:
                num = Character.getNumericValue(carta.charAt(0));
        }
        return num;
    }

    private boolean validarGanador2() {
        boolean ganador = false;
        if (suma1 == 21) {
            ganador = true;
            valorTotal1.setTextColor(Color.YELLOW);
            jugardeNuevo("GANADOR J1");
        } else if (suma2 == 21) {
            ganador = true;
            jugardeNuevo("GANADOR J2");
            valorTotal2.setTextColor(Color.YELLOW);
        }
        return ganador;
    }

    private void validarGanador() {
        String texto = "";

        if (suma1 > 21) suma1 = 0;
        if (suma2 > 21) suma2 = 0;
        if (suma3 > 21) suma3 = 0;

        if (suma1 <= 21 && suma1 > suma2 && suma1 > suma3) {
            texto = "GANADOR J1";
        } else if (suma2 <= 21 && suma2 > suma1 && suma2 > suma3) {
            texto = "GANADOR J2";
        } else if (suma3 <= 21 && suma3 > suma1 && suma3 > suma2) {
            texto = "GANA EL CRUPIER";
        } else {
            texto = "EMPATE !";
        }

        jugardeNuevo(texto);
    }

    public void jugardeNuevo(String texto) {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle(texto);
        dialogo.setMessage("¿Quereis jugar de nuevo?");
        dialogo.setCancelable(false);
        dialogo.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                Intent intent = new Intent(BlackJack.this, BlackJack.class);
                startActivity(intent);
                finish();
                resetearActividad();
            }
        });
        dialogo.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

                Intent intent = new Intent(BlackJack.this, VistaPrincipal.class);
                startActivity(intent);
                finish();
                resetearActividad();
            }
        });
        dialogo.show();
    }

    public void plantarse1(View view) {
        vibrar();
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("¿Estás seguro que quieres plantarte?");
        dialogo.setCancelable(false);
        dialogo.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                Toast.makeText(getApplicationContext(), "Turno Jugador 2", Toast.LENGTH_LONG).show();
                plantarse2.setClickable(true);
                turnoJ1 = false;
                plantarse1.setClickable(false);
            }

        });
        dialogo.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

            }
        });
        dialogo.show();
    }

    public void plantarse2(View view) {
        vibrar();
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("¿Estás seguro que quieres plantarte?");
        dialogo.setCancelable(false);
        dialogo.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                turnoCrupier();
            }

        });
        dialogo.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

            }
        });
        dialogo.show();
    }

    private void turnoCrupier() {

        cogerCarta.setClickable(false);
        plantarse2.setClickable(false);
        Toast.makeText(getApplicationContext(), "TURNO CRUPIER", Toast.LENGTH_SHORT).show();
        while (suma3 <= 16) {
            String imageCrupier1 = "https://deckofcardsapi.com/static/img/" + manoCrupier.get(posicionManoCrupier) + ".png";
            Picasso.get().load(imageCrupier1).into(listaImagesCrupier.get(posicionManoCrupier));
            listaImagesCrupier.get(posicionManoCrupier).setVisibility(View.VISIBLE);

            suma3 = verificarValorAs(manoCrupier, posicionManoCrupier, valorTotalCrupier);

            posicionManoCrupier++;

        }

        if (suma3 > 21) {
            valorTotalCrupier.setTextColor(Color.RED);
        }
        if (suma3 == 21) {
            valorTotalCrupier.setTextColor(Color.YELLOW);
        }

        new Handler().postDelayed(() -> validarGanador(), 3500);
    }

    private void resetearActividad() {
        turnoJ1 = true;
        suma1 = 0;
        suma2 = 0;
        suma3 = 0;
        manoJ1.clear();
        manoJ2.clear();
        manoCrupier.clear();
        posicionManoJ1 = 2;
        posicionManoJ2 = 2;
        posicionManoCrupier = 1;
        listaImagesJ1.clear();
        listaImagesJ2.clear();
        listaImagesCrupier.clear();
    }

    private void vibrar() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(100);
        }
    }

    private int verificarValorAs(ArrayList<String> manoJugador, int posicionJugador, TextView valorJugador) {
        int sumaJugador;
        int cantidadAses = 0;
        int valorTotalSinAses = 0;
        Log.i("TAG", manoJugador.toString());
        // Calcular el valor total del jugador sin contar los Ases y contar cuántos Ases hay
        for (int i = 0; i <= posicionJugador; i++) {
            if (manoJugador.get(i).charAt(0) == 'A' || manoJugador.get(i).charAt(0) == 'a') {
                cantidadAses++;
            } else {
                valorTotalSinAses += devolverValorCarta(manoJugador.get(i));
            }
        }
        Log.i("TAG", Integer.toString(cantidadAses));
        Log.i("TAG", Integer.toString(valorTotalSinAses));
        Log.i("TAG", Integer.toString(posicionJugador));
        // Calcular el valor de los Ases de forma óptima
        int nuevovalorTotal = valorTotalSinAses;
        for (int i = 0; i < cantidadAses; i++) {
            if (nuevovalorTotal + 11 <= 21) {
                nuevovalorTotal += 11;
            } else {
                nuevovalorTotal += 1;
            }
        }
        Log.i("TAG", Integer.toString(nuevovalorTotal));
        sumaJugador = nuevovalorTotal;
        valorJugador.setText(Integer.toString(sumaJugador));

        return sumaJugador;
    }
}