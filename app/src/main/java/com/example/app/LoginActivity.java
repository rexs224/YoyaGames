package com.example.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    EditText email;
    EditText password;
    Button login;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistalogin);

        email = findViewById(R.id.entryCorreo1);
        password = findViewById(R.id.entryPassword1);
        login = findViewById(R.id.loginbutton);
    }
    public void logear(View v) {
        String mail = String.valueOf(email.getText());
        String contrasena = String.valueOf(password.getText());
        mAuth.signInWithEmailAndPassword(mail, contrasena)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Inicio de sesión exitoso
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (mail.equals("admin@gmail.com")) {
                            cambiarAdmin();
                        } else {
                            cambiar();
                        }
                        // Guarda una referencia al usuario actual
                        saveCurrentUser(user);
                    } else {
                        // Error en el inicio de sesión
                        Toast.makeText(LoginActivity.this, "Error de inicio de sesión", Toast.LENGTH_SHORT).show();
                        Toast.makeText(LoginActivity.this, mail, Toast.LENGTH_SHORT).show();
                        Toast.makeText(LoginActivity.this, contrasena, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveCurrentUser(FirebaseUser user) {
        // Aquí puedes guardar la referencia al usuario actual en una variable global
        // o en una clase de utilidad para acceder a ella desde otras partes de la aplicación
        // Por ejemplo:
        CurrentUser.setCurrentUser(user);
    }


    private void cambiarAdmin() {
        Intent intent = new Intent(LoginActivity.this, VistaAdmin.class);
        startActivity(intent);
        finish();
    }

    public void cambiar() {
        Intent intent = new Intent(LoginActivity.this, VistaPrincipal.class);
        startActivity(intent);
        finish();
    }

//

}
