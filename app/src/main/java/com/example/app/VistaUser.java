package com.example.app;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.ComponentActivity;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class VistaUser extends ComponentActivity {

    EditText nombre;
    EditText email;
    EditText password;
    Button update;
    Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistauser);

        nombre=findViewById(R.id.usuarioUser);
        email=findViewById(R.id.mailUser);
        password=findViewById(R.id.contrasenaUser);
        update = findViewById(R.id.updateButtonUser);
        delete = findViewById(R.id.deleteButtonUser);

        getCurrentUser();

    }

    public void borrarUser(View v){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        String mail=String.valueOf(email.getText());

        db.collection("users")
                .whereEqualTo("mail", mail)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String uid = document.getId();
                            // Eliminar el usuario de la base de datos Firestore
                            db.collection("users").document(uid).delete()
                                    .addOnSuccessListener(aVoid -> {
                                        // Eliminar el usuario de la autenticación de Firebase
                                        auth.signInWithEmailAndPassword(mail, "123456") // Usamos una contraseña temporal
                                                .addOnCompleteListener(authTask -> {
                                                    if (authTask.isSuccessful()) {
                                                        FirebaseUser user = auth.getCurrentUser();
                                                        if (user != null) {
                                                            user.delete()
                                                                    .addOnCompleteListener(deleteTask -> {
                                                                        if (deleteTask.isSuccessful()) {
                                                                            Intent intent = new Intent(VistaUser.this, LoginActivity.class);
                                                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                            startActivity(intent);
                                                                        } else {
                                                                            Log.d("deleteAccount", "No borrado.");
                                                                        }
                                                                    });
                                                        }
                                                    } else {
                                                        Log.d("signInWithEmailAndPassword", "Failed.");
                                                    }
                                                });
                                    })
                                    .addOnFailureListener(e -> Log.d("borrar", "Error al borrar usuario de Firestore: " + e.getMessage()));
                        }
                    } else {
                        Log.d("borrar", "Error al obtener usuario de Firestore: ", task.getException());
                    }
                });



    }

    public void updateUser(View v) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String mail = email.getText().toString();
        String nick = nombre.getText().toString();
        String contrasena = password.getText().toString();

        Map<String, Object> newData = new HashMap<>();
        newData.put("nick", nick);
        newData.put("mail", mail);
        newData.put("contrasena", contrasena);
        newData.put("puntos", "0");

        db.collection("users")
                .whereEqualTo("mail", mail)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String uid = document.getId();
                            // Actualizar los datos del usuario en la base de datos Firestore
                            db.collection("users").document(uid).update(newData)
                                    .addOnSuccessListener(aVoid -> {
                                        Log.d("actualizar", "Datos actualizados exitosamente en Firestore.");
                                    })
                                    .addOnFailureListener(e -> Log.d("actualizar", "Error al actualizar datos en Firestore: " + e.getMessage()));
                        }
                    } else {
                        Log.d("actualizar", "Error al obtener usuario de Firestore: ", task.getException());
                    }
                });
    }
    public void getCurrentUser() {
        FirebaseUser currentUser = CurrentUser.getCurrentUser();
        if (currentUser != null) {
            email.setText(currentUser.getEmail());
        } else {
            Log.d("getCurrentUser", "No hay un usuario actual");
        }
    }




}

