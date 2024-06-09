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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class VistaAdmin extends ComponentActivity {

    EditText nombre;
    EditText email;
    EditText password;
    Button update;
    Button delete;
    ImageView menor;
    ImageView mayor;
    int numUser=0;
    List<User> lista=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistaadmin);


        nombre=findViewById(R.id.usuario);
        email=findViewById(R.id.mail);
        password=findViewById(R.id.contrasena);
        update = findViewById(R.id.updateButton);
        delete = findViewById(R.id.deleteButton);
        menor = findViewById(R.id.menorUser);
        mayor = findViewById(R.id.mayorUser);

        getAllUsers();

    }

    public void borrar(View v){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailUser = auth.getCurrentUser().getEmail();
        String name = auth.getCurrentUser().getDisplayName();

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
                                                                            Intent intent = new Intent(VistaAdmin.this, LoginActivity.class);
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

    public void update(View v) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
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
    public void getAllUsers() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<User> userList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            User user = document.toObject(User.class);
                            userList.add(user);
                            lista.add(user);
                            User firstUser = lista.get(0);
                            nombre.setText(firstUser.getNick());
                            email.setText(firstUser.getMail());
                            password.setText(firstUser.getContrasena());

                        }
                        Log.d("getAllUsers", "User list size: " + userList.size());
                        Log.d("getAllUsers", "User list size: " + userList.get(0).getNick());
                    } else {
                        Log.d("getAllUsers", "Error al obtener usuarios de Firestore: ", task.getException());
                    }
                });
    }
    public void bajar(View v) {
        if(numUser==0){
            menor.setEnabled(false);
        }else {
            numUser--;
            mayor.setEnabled(true);
        }
        if (!lista.isEmpty()) {
            menor.setEnabled(true);
            User user = lista.get(numUser);
            nombre.setText(user.getNick());
            email.setText(user.getMail());
            password.setText(user.getContrasena());
        }
    }
    public void subir(View v) {
        if(numUser==(lista.size()-1)){
            mayor.setEnabled(false);
        }else{
            numUser++;
            menor.setEnabled(true);
        }
        if (!lista.isEmpty()) {
            menor.setEnabled(true);
            User user = lista.get(numUser);
            nombre.setText(user.getNick());
            email.setText(user.getMail());
            password.setText(user.getContrasena());
        }
    }



}
