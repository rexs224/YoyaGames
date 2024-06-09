package com.example.app;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.app.AlertDialog;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;


public class SecondActivity extends AppCompatActivity {

    Button J1_1;
    Button J1_2;
    Button J1_3;
    Button J1_4;
    Button J2_1;
    Button J2_2;
    Button J2_3;
    Button J2_4;
    TextView pantalla1;
    TextView pantalla2;
    TextView restantes1;
    TextView restantes2;
    TextView puntos1;
    TextView puntos2;
    TextView cuentaAtras;
    TextView texto1;
    ImageView fondoOscuro;
    AlertDialog.Builder builder;



    int numero1;
    int numero2;
    String simbolo;
    int correcto=0;
    int restantes=10;
    int PJ1=0;
    int PJ2=0;
    ArrayList<Button> botonesJ1 = new ArrayList<Button>();
    ArrayList<Button> botonesJ2 = new ArrayList<Button>();
    ArrayList<String> simbolos = new ArrayList<String>();



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mates);

        fondoOscuro = findViewById(R.id.fondooscuro);
        cuentaAtras = findViewById(R.id.cuentaAtras);
        texto1 = findViewById(R.id.tww);
        J1_1 = findViewById(R.id.J1_1);
        J1_2 = findViewById(R.id.J1_2);
        J1_3 = findViewById(R.id.J1_3);
        J1_4 = findViewById(R.id.J1_4);
        J2_1 = findViewById(R.id.J2_1);
        J2_2 = findViewById(R.id.J2_2);
        J2_3 = findViewById(R.id.J2_3);
        J2_4 = findViewById(R.id.J2_4);
        pantalla1 = findViewById(R.id.pantalla1);
        pantalla2 = findViewById(R.id.pantalla2);
        restantes1 = findViewById(R.id.restantes1);
        restantes2 = findViewById(R.id.restantes2);
        puntos1 = findViewById(R.id.puntos1);
        puntos2 = findViewById(R.id.puntos2);

        botonesJ1.add(J1_1);
        botonesJ1.add(J1_2);
        botonesJ1.add(J1_3);
        botonesJ1.add(J1_4);

        botonesJ2.add(J2_1);
        botonesJ2.add(J2_2);
        botonesJ2.add(J2_3);
        botonesJ2.add(J2_4);

        simbolos.add("-");
        simbolos.add("+");
        simbolos.add("*");

        puntos1.setText("0");
        puntos2.setText("0");
        restantes1.setText("11");
        restantes2.setText("11");

        correcto=0;
        restantes=11;
        PJ1=0;
        PJ2=0;

        builder = new AlertDialog.Builder(this);

        new CountDownTimer(4000, 1000) {
            public void onTick(long millisUntilFinished) {
                cuentaAtras.setText(Integer.toString(Integer.parseInt(cuentaAtras.getText().toString()) - 1));
            }

            public void onFinish() {
                realizarEjercicio();
            }
        }.start();
    }

    private void realizarEjercicio(){
        ArrayList<Integer> respuestas = new ArrayList<Integer>();
        cuentaAtras.setVisibility(View.INVISIBLE);
        fondoOscuro.setVisibility(View.INVISIBLE);
        texto1.setVisibility(View.INVISIBLE);

        for(int i=0;i<botonesJ1.size();i++){
            botonesJ1.get(i).setBackgroundColor(Color.parseColor("#64B5F6"));
            botonesJ2.get(i).setBackgroundColor(Color.parseColor("#64B5F6"));
        }

        int numero1=((int) (Math.random()*10));
        int numero2=((int) (Math.random()*10));
        String simbolo=simbolos.get((int) (Math.random()*simbolos.size()));

        String operacion=Integer.toString(numero1)+simbolo+Integer.toString(numero2);
        switch (simbolo){
            case "+":
                correcto=numero1+numero2;
                break;

            case "-":
                correcto=numero1-numero2;
                break;

            case "*":
                correcto=numero1*numero2;
                break;
        }
        pantalla1.setText(operacion);
        pantalla2.setText(operacion);

        respuestas.add(correcto);
        for (int i=0;i<3;i++){
            respuestas.add((int) (Math.random() * (50 + 10 + 1) - 10));
        }
        Collections.shuffle(respuestas);
        for (int i=0;i<botonesJ1.size();i++){
            botonesJ1.get(i).setText(Integer.toString(respuestas.get(i)));
        }
        Collections.shuffle(respuestas);
        for (int i=0;i<botonesJ2.size();i++){
            botonesJ2.get(i).setText(Integer.toString(respuestas.get(i)));
        }
    }

    public void solucionar(View view) {
        Button boton = (Button) view;
        int respuesta=Integer.parseInt((String)boton.getText());
        if (restantes<=1){
            restantes1.setText("0");
            restantes2.setText("0");
            if(PJ1>PJ2){
                builder.setTitle("Se acabó")
                        .setMessage("Ha ganado el J1")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(SecondActivity.this, SecondActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(SecondActivity.this, VistaPrincipal.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                builder.show();

            }else{
                builder.setTitle("Se acabó")
                        .setMessage("Ha ganado el J2")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(SecondActivity.this, SecondActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(SecondActivity.this, VistaPrincipal.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                builder.show();
            }
        }else if(respuesta==correcto){
            realizarEjercicio();
            if(boton.equals(J1_1)||boton.equals(J1_2)||boton.equals(J1_3)||boton.equals(J1_4)){
                PJ1++;
                restantes--;
                puntos1.setText(Integer.toString(PJ1));
                restantes1.setText(Integer.toString(restantes));
                restantes2.setText(Integer.toString(restantes));
            }else if(boton.equals(J2_1)||boton.equals(J2_2)||boton.equals(J2_3)||boton.equals(J2_4)){
                PJ2++;
                restantes--;
                puntos2.setText(Integer.toString(PJ2));
                restantes1.setText(Integer.toString(restantes));
                restantes2.setText(Integer.toString(restantes));
            }
        }else{
            boton.setBackgroundColor(Color.parseColor("#FF0000"));
        }
    }
}