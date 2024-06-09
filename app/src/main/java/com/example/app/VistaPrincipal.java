package com.example.app;

import androidx.activity.ComponentActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.google.firebase.auth.FirebaseAuth;


public class VistaPrincipal extends ComponentActivity {
    ViewFlipper flipper;
    ImageView imgCaballos;
    ImageView imgBlackjack;
    ImageView imgMates;
    ImageView imgTiempo;
    ImageView imgParejas;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistaprincipal);

        auth = FirebaseAuth.getInstance();

        imgCaballos = findViewById(R.id.imgcaballos);
        imgBlackjack = findViewById(R.id.imgblackjack);
        imgMates = findViewById(R.id.imgmates);
        imgTiempo = findViewById(R.id.imgcronometro);
        imgParejas = findViewById(R.id.imgparejas);

        int[] imagenes={R.drawable.carruselblackjack, R.drawable.carruselcaballos, R.drawable.carruselmates, R.drawable.carruselbotones};
        flipper=findViewById(R.id.viewFlipper);

        for(int imagen: imagenes){
            flipperImages(imagen);
        }
        flipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentImagePosition = flipper.getDisplayedChild();

                switch (currentImagePosition) {
                    case 0:
                        cambiar0(v);
                        break;
                    case 1:
                        cambiar1(v);
                        break;
                    case 2:
                        cambiar2(v);
                        break;
                    case 3:
                        cambiar3(v);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void flipperImages(int image){
        ImageView imageView=new ImageView(this);
        imageView.setBackgroundResource(image);

        flipper.addView(imageView);
        flipper.setFlipInterval(3000);
        flipper.setAutoStart(true);

        flipper.setInAnimation(this, android.R.anim.slide_in_left);
        flipper.setOutAnimation(this, android.R.anim.slide_out_right);
    }
    public void cambiar0(View view) {
        Intent intent = new Intent(this, BlackJack.class);
        startActivity(intent);
    }
    public void cambiar1(View view) {
        Intent intent = new Intent(this, CarreraCaballos.class);
        startActivity(intent);
    }
    public void cambiar2(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    public void cambiar3(View view) {
        Intent intent = new Intent(this, TiempoActivity.class);
        startActivity(intent);
    }
    public void cambiar4(View view) {
        Intent intent = new Intent(this, BuscarParejaCarta.class);
        startActivity(intent);
    }
    public void cambiar5(View view) {
        Intent intent = new Intent(this, DamasActivity.class);
        startActivity(intent);
    }

    public void logout (View view){
        auth.signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }

    public void cambiarVistaUser(View view){
        Intent intent = new Intent(this, VistaUser.class);
        startActivity(intent);
    }
}