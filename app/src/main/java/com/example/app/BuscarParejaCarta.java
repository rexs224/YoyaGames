package com.example.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.ComponentActivity;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app.R;

import java.util.ArrayList;
import java.util.Collections;

public class BuscarParejaCarta extends AppCompatActivity {
    private ImageView item11;
    private ImageView item12;
    private ImageView item13;
    private ImageView item14;
    private ImageView item21;
    private ImageView item22;
    private ImageView item23;
    private ImageView item24;
    private ImageView item31;
    private ImageView item32;
    private ImageView item33;
    private ImageView item34;

    private TextView player1;
    private TextView player2;
    private ImageButton sonido;
    private MediaPlayer mp;
    private MediaPlayer mpFondo;
    private ImageView imagen1;
    private ImageView imagen2;

    private ArrayList<Integer> imagenesArray = new ArrayList<>();
    private int tetris = 0;
    private int snowbros = 0;
    private int pacman = 0;
    private int space = 0;
    private int street = 0;
    private int supermario = 0;
    private int turno = 1;
    private int puntosj1 = 0;
    private int puntosj2 = 0;
    private int numeroImgSelect = 1;
    private boolean musicaOn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscarparejacarta);
        inicializarInterfaz();
    }

    private void inicializarInterfaz() {
        item11 = findViewById(R.id.item11);
        item12 = findViewById(R.id.item12);
        item13 = findViewById(R.id.item13);
        item14 = findViewById(R.id.item14);
        item21 = findViewById(R.id.item21);
        item22 = findViewById(R.id.item22);
        item23 = findViewById(R.id.item23);
        item24 = findViewById(R.id.item24);
        item31 = findViewById(R.id.item31);
        item32 = findViewById(R.id.item32);
        item33 = findViewById(R.id.item33);
        item34 = findViewById(R.id.item34);

        sonido = findViewById(R.id.sonido);
        sonido.setColorFilter(Color.GREEN);
        musica("musicafondo", true);
        item11.setTag("0");
        item12.setTag("1");
        item13.setTag("2");
        item14.setTag("3");
        item21.setTag("4");
        item22.setTag("5");
        item23.setTag("6");
        item24.setTag("7");
        item31.setTag("8");
        item32.setTag("9");
        item33.setTag("10");
        item34.setTag("11");

        tetris = R.drawable.tetris;
        pacman = R.drawable.pacman;
        snowbros = R.drawable.snowbros;
        space = R.drawable.spaceinvaders;
        street = R.drawable.street;
        supermario = R.drawable.supermario;

        imagenesArray.add(11);
        imagenesArray.add(12);
        imagenesArray.add(13);
        imagenesArray.add(14);
        imagenesArray.add(15);
        imagenesArray.add(16);
        imagenesArray.add(21);
        imagenesArray.add(22);
        imagenesArray.add(23);
        imagenesArray.add(24);
        imagenesArray.add(25);
        imagenesArray.add(26);

        Collections.shuffle(imagenesArray);

        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);

        player2.setTextColor(Color.GRAY);
        player1.setTextColor(Color.WHITE);
    }

    private void musica(String s, boolean loop) {
        int resID = getResources().getIdentifier(s, "raw", getPackageName());
        if (s.equals("musicafondo")) {
            mpFondo = MediaPlayer.create(this, resID);
            mpFondo.setLooping(loop);
            mpFondo.setVolume(5.18F, 5.19F);
            if (!mpFondo.isPlaying()) {
                mpFondo.start();
            }
        } else {
            mp = MediaPlayer.create(this, resID);
            mp.setVolume(9.18F, 9.19F);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
            });

        }
    }

    public void musicaFondo(View view) {
        if (musicaOn) {
            mpFondo.pause();
            sonido.setImageResource(R.drawable.volumen_off);
            sonido.setColorFilter(Color.GRAY);
        } else {
            mpFondo.start();
            sonido.setImageResource(R.drawable.volumen_on);
            sonido.setColorFilter(Color.GREEN);
        }
        musicaOn = !musicaOn;
    }

    public void seleccionar(View imagen) {
        musica("touch", true);
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(100);
        }
        verificar(imagen);
    }

    private void verificar(View imagen) {
        // Animation animation = AnimationUtils.loadAnimation(this, R.anim.animations);
        ImageView imageview = (ImageView) imagen;
        int tag = Integer.parseInt(imagen.getTag().toString());
        switch (imagenesArray.get(tag)) {
            case 11:
                imageview.setImageResource(pacman);
                // imageview.startAnimation(animation);
                break;
            case 12:
                imageview.setImageResource(tetris);
                break;
            case 13:
                imageview.setImageResource(snowbros);
                break;
            case 14:
                imageview.setImageResource(space);
                break;
            case 15:
                imageview.setImageResource(street);
                break;
            case 16:
                imageview.setImageResource(supermario);
                break;
            case 21:
                imageview.setImageResource(pacman);
                break;
            case 22:
                imageview.setImageResource(tetris);
                break;
            case 23:
                imageview.setImageResource(snowbros);
                break;
            case 24:
                imageview.setImageResource(space);
                break;
            case 25:
                imageview.setImageResource(street);
                break;
            case 26:
                imageview.setImageResource(supermario);
                break;
        }
        if(numeroImgSelect == 1){
            imagen1 = imageview;
            numeroImgSelect = 2;
            imageview.setEnabled(false);
        }else if(numeroImgSelect == 2){
            imagen2 = imageview;
            numeroImgSelect = 1;
            imageview.setEnabled(false);
            deshabilitarImagenes();
            Handler h = new Handler(Looper.getMainLooper());
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    sonImagenesIguales();
                }
            }, 1000);
        }
    }

    private void sonImagenesIguales() {
        if(imagen1.getDrawable().getConstantState() == imagen2.getDrawable().getConstantState()){
            musica("success",false);
            if(turno == 1){
                puntosj1++;
                player1.setText("PLAYER 1: " + puntosj1);
            }else{
                puntosj2++;
                player2.setText("PLAYER 2: " + puntosj2);
            }
            imagen1.setEnabled(false);
            imagen2.setEnabled(false);
            imagen1.setTag("");
            imagen2.setTag("");
        }else{
            musica("no",false);
            imagen1.setImageResource(R.drawable.oculta);
            imagen2.setImageResource(R.drawable.oculta);
            if(turno==1){
                turno = 2;
                player1.setTextColor(Color.GRAY);
                player2.setTextColor(Color.WHITE);
            }else{
                turno = 1;
                player2.setTextColor(Color.GRAY);
                player1.setTextColor(Color.WHITE);
            }
        }
        item11.setEnabled(!item11.getTag().toString().isEmpty());
        item12.setEnabled(!item12.getTag().toString().isEmpty());
        item13.setEnabled(!item13.getTag().toString().isEmpty());
        item14.setEnabled(!item14.getTag().toString().isEmpty());
        item21.setEnabled(!item21.getTag().toString().isEmpty());
        item22.setEnabled(!item22.getTag().toString().isEmpty());
        item23.setEnabled(!item23.getTag().toString().isEmpty());
        item24.setEnabled(!item24.getTag().toString().isEmpty());
        item31.setEnabled(!item31.getTag().toString().isEmpty());
        item32.setEnabled(!item32.getTag().toString().isEmpty());
        item33.setEnabled(!item33.getTag().toString().isEmpty());
        item34.setEnabled(!item34.getTag().toString().isEmpty());
        verificarGanador();
    }

    private void verificarGanador() {
        boolean check1 = item11.getTag().toString().isEmpty();
        boolean check2 = item12.getTag().toString().isEmpty();
        boolean check3 = item13.getTag().toString().isEmpty();
        boolean check4 = item14.getTag().toString().isEmpty();
        boolean check5 = item21.getTag().toString().isEmpty();
        boolean check6 = item22.getTag().toString().isEmpty();
        boolean check7 = item23.getTag().toString().isEmpty();
        boolean check8 = item24.getTag().toString().isEmpty();
        boolean check9 = item31.getTag().toString().isEmpty();
        boolean check11 = item32.getTag().toString().isEmpty();
        boolean check12= item33.getTag().toString().isEmpty();
        boolean check13 = item34.getTag().toString().isEmpty();
        if (check1 && check2 && check3 && check4 && check5 && check6 && check7 && check8 && check9 && check11 && check12 && check13) {
            mp.stop();
            mp.release();
            mpFondo.stop();
            musica("win",false);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Fin del Juego")
                    .setMessage("PUNTAJE FINAL \nJ1: " + puntosj1 + "\nJ2: " + puntosj2)
                    .setCancelable(false)
                    .setPositiveButton("Jugar de nuevo", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(BuscarParejaCarta.this, BuscarParejaCarta.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("Finalizar Juego", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(BuscarParejaCarta.this, VistaPrincipal.class);
                            startActivity(intent);
                            finish();
                            mpFondo.stop();
                        }
                    });
            AlertDialog ad = builder.create();
            ad.show();
        }
    }

    private void deshabilitarImagenes() {
        item11.setEnabled(false);
        item12.setEnabled(false);
        item13.setEnabled(false);
        item14.setEnabled(false);
        item21.setEnabled(false);
        item22.setEnabled(false);
        item23.setEnabled(false);
        item24.setEnabled(false);
        item31.setEnabled(false);
        item32.setEnabled(false);
        item33.setEnabled(false);
        item34.setEnabled(false);
    }





}