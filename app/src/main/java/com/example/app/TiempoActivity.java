package com.example.app;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TiempoActivity extends AppCompatActivity {
    TextView marca1;
    TextView marca2;
    CountUpTimer timer1;
    CountUpTimer timer2;
    TextView cuentaAtras;
    TextView texto1;
    ImageView fondoOscuro;
    ImageButton boton1;
    ImageButton boton2;
    AlertDialog.Builder builder;
    int tiempoJ1;
    int tiempoJ2;
    int tiempoPartida;
    Spinner spinner;
    boolean boton1Activado;
    boolean boton2Activado;
    private Thread daemonThread;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiempo);

        spinner = findViewById(R.id.spinner);
        cuentaAtras=findViewById(R.id.cuentaAtras);
        fondoOscuro = findViewById(R.id.fondooscuro);
        texto1 = findViewById(R.id.tww);
        boton1 = findViewById(R.id.boton1);
        boton2 = findViewById(R.id.boton2);
        marca1 = findViewById(R.id.marca1);
        marca2 = findViewById(R.id.marca2);

        cuentaAtras.setVisibility(View.INVISIBLE);
        fondoOscuro.setVisibility(View.INVISIBLE);
        texto1.setVisibility(View.INVISIBLE);
        boton1Activado=false;
        boton2Activado=false;

        builder = new AlertDialog.Builder(this);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.opciones, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void jugar(View view){
        cuentaAtras.setVisibility(View.VISIBLE);
        fondoOscuro.setVisibility(View.VISIBLE);
        texto1.setVisibility(View.VISIBLE);

        cuentaAtras.setText("4");
        boton1Activado=false;
        boton2Activado=false;
        builder = new AlertDialog.Builder(this);

        startDaemonThread();

        marca1.setText("");
        marca2.setText("");
        new CountDownTimer(4000, 1000) {
            public void onTick(long millisUntilFinished) {
                cuentaAtras.setText(Integer.toString(Integer.parseInt(cuentaAtras.getText().toString()) - 1));
            }

            public void onFinish() {
                empezar();
            }
        }.start();
    }
    private void startTimer() {
        if (timer1 == null) {
            timer1 = new CountUpTimer() {
                @Override
                public void onTick(long elapsedTime) {
                    tiempoJ1= getSeconds();
                }
            };
            timer1.start();
        }
        if (timer2 == null) {
            timer2 = new CountUpTimer() {
                @Override
                public void onTick(long elapsedTime) {
                    tiempoJ2= getSeconds();
                }
            };
            timer2.start();
        }
    }

    public void stopTimer1(View view) {
        if (timer1 != null) {
            timer1.stop();
            timer1 = null;
        }
        boton1Activado=true;
    }
    public void stopTimer2(View view) {
        if (timer2 != null) {
            timer2.stop();
            timer2 = null;
        }
        boton2Activado=true;
    }
    public void empezar(){
        cuentaAtras.setVisibility(View.INVISIBLE);
        fondoOscuro.setVisibility(View.INVISIBLE);
        texto1.setVisibility(View.INVISIBLE);
        Object item=spinner.getSelectedItem();
        tiempoPartida = Integer.parseInt(String.valueOf(item));
        startTimer();
    }
    private void startDaemonThread() {
        daemonThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (boton1Activado && boton2Activado) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                marca1.setText(String.valueOf(tiempoJ1));
                                marca2.setText(String.valueOf(tiempoJ2));
                                if(tiempoJ1>tiempoPartida && tiempoJ2>tiempoPartida){
                                    if(tiempoJ1<tiempoJ2){
                                        builder.setTitle("Se acabó")
                                                .setMessage("Ha ganado el J1")
                                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                    }
                                                })
                                                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        Intent intent = new Intent(TiempoActivity.this, VistaPrincipal.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                });
                                        builder.show();
                                    }else if (tiempoJ2<tiempoJ1){
                                        builder.setTitle("Se acabó")
                                                .setMessage("Ha ganado el J2")
                                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                    }
                                                })
                                                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        Intent intent = new Intent(TiempoActivity.this, VistaPrincipal.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                });
                                        builder.show();
                                    }else{
                                        builder.setTitle("Se acabó")
                                                .setMessage("EMPATE")
                                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                    }
                                                })
                                                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        Intent intent = new Intent(TiempoActivity.this, VistaPrincipal.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                });
                                        builder.show();
                                    }
                                }else if (tiempoJ1<tiempoPartida && tiempoJ2<tiempoPartida) {
                                    if(tiempoJ1>tiempoJ2){
                                        builder.setTitle("Se acabó")
                                                .setMessage("Ha ganado el J1")
                                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                    }
                                                })
                                                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        Intent intent = new Intent(TiempoActivity.this, VistaPrincipal.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                });
                                        builder.show();
                                    }else if (tiempoJ2>tiempoJ1){
                                        builder.setTitle("Se acabó")
                                                .setMessage("Ha ganado el J2")
                                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                    }
                                                })
                                                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        Intent intent = new Intent(TiempoActivity.this, VistaPrincipal.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                });
                                        builder.show();
                                    }else{
                                        builder.setTitle("Se acabó")
                                                .setMessage("EMPATE")
                                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                    }
                                                })
                                                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        Intent intent = new Intent(TiempoActivity.this, VistaPrincipal.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                });
                                        builder.show();
                                    }
                                }else{
                                    if(tiempoJ1<tiempoPartida){
                                        tiempoJ1=tiempoJ1-tiempoPartida*(-1);
                                    }else{
                                        tiempoJ1=tiempoJ1-tiempoPartida;
                                    }
                                    if(tiempoJ2<tiempoPartida){
                                        tiempoJ2=tiempoJ2-tiempoPartida*(-1);
                                    }else{
                                        tiempoJ2=tiempoJ2-tiempoPartida;
                                    }
                                    if(tiempoJ1<tiempoJ2){
                                        builder.setTitle("Se acabó")
                                                .setMessage("Ha ganado el J1")
                                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                    }
                                                })
                                                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        Intent intent = new Intent(TiempoActivity.this, VistaPrincipal.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                });
                                        builder.show();
                                    }else if (tiempoJ2<tiempoJ1){
                                        builder.setTitle("Se acabó")
                                                .setMessage("Ha ganado el J2")
                                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                    }
                                                })
                                                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        Intent intent = new Intent(TiempoActivity.this, VistaPrincipal.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                });
                                        builder.show();
                                    }else{
                                        builder.setTitle("Se acabó")
                                                .setMessage("EMPATE")
                                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                    }
                                                })
                                                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        Intent intent = new Intent(TiempoActivity.this, VistaPrincipal.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                });
                                        builder.show();
                                    }
                                }
                            }
                        });
                        break;
                    }
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        daemonThread.setDaemon(true);
        daemonThread.start();
    }
}
