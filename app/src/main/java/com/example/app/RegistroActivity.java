package com.example.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText nombre;
    EditText email;
    EditText password;
    Button register;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistaregistro);

        nombre = findViewById(R.id.entryLogin);
        email = findViewById(R.id.entryCorreo2);
        password = findViewById(R.id.entryPassword2);
    }

    public void registrar(View v){
        String nick=String.valueOf(nombre.getText());
        String mail=String.valueOf(email.getText());
        String contrasena=String.valueOf(password.getText());
        String puntos="0";

        Map<String, Object> userData = new HashMap<>();
        userData.put("nick", nick);
        userData.put("mail", mail);
        userData.put("contrasena", contrasena);
        userData.put("puntos", puntos);

        mAuth.createUserWithEmailAndPassword(mail, contrasena)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Registro exitoso, ahora puedes guardar información adicional en Firebase Realtime Database
                            FirebaseUser user = mAuth.getCurrentUser();
                            String userId = user.getUid();

                            // Crea una referencia a la base de datos de Firebase
                            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

                            // Guarda los datos del usuario en Firebase Realtime Database
                            db.collection("users").document(userId)
                                    .set(userData)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(RegistroActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                                            cambiar();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Error al registrar usuario en Cloud Firestore
                                            Toast.makeText(RegistroActivity.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            // Error en el registro de usuario en Firebase Authentication
                            Toast.makeText(RegistroActivity.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void cambiar() {
        Intent intent = new Intent(RegistroActivity.this, VistaPrincipal.class);
        startActivity(intent);
        finish();
    }
}
