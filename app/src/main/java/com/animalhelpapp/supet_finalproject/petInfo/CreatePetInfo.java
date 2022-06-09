package com.animalhelpapp.supet_finalproject.petInfo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.animalhelpapp.supet_finalproject.MainActivity;
import com.animalhelpapp.supet_finalproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreatePetInfo extends AppCompatActivity {

    Button create_btn;
    TextView namePet, title;
    EditText description;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pet_info);

        //Inicializar Firebase
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        /*Conectar con la parte gráfica*/
        namePet = findViewById(R.id.createPetInfo_petName);
        title = findViewById(R.id.createPetInfo_titleCard);
        description = findViewById(R.id.createPetInfo_description);

        /*Botón*/
        create_btn = findViewById(R.id.createPetInfo_btn);
        create_btn.setOnClickListener(v -> {
            /*leer los campos*/
            String nombre = namePet.getText().toString().trim();
            String  titulo = title.getText().toString().trim();
            String descripcion = description.getText().toString().trim();

            if (nombre.isEmpty() || titulo.isEmpty() || descripcion.isEmpty()) {
                Toast.makeText(CreatePetInfo.this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                crearPetInfo(nombre, titulo, descripcion);
            }
        });

    }

    /*método crearPetInfo*/
    private void crearPetInfo(String nombre, String titulo, String descripcion) {
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null) {
            String uid = mAuth.getCurrentUser().getUid();
            CollectionReference userCollection = firebaseFirestore.collection("user");
            Map<String, Object> map2 = new HashMap<>();
            map2.put("petName", nombre);
            map2.put("title", titulo);
            map2.put("description", descripcion);
            
            /*guardar los datos en firestore*/
            userCollection.document(uid).collection("petInfo").add(map2).addOnSuccessListener(unused -> {
                finish();
                startActivity(new Intent(CreatePetInfo.this, MainActivity.class));
                Toast.makeText(CreatePetInfo.this, "La tarjeta se ha creado con éxito", Toast.LENGTH_SHORT).show();

            }).addOnFailureListener(e -> Toast.makeText(CreatePetInfo.this, "Error al crear tarjeta", Toast.LENGTH_SHORT).show());
        }
    }

    /*para que funcione el getUid()*/
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if(firebaseUser != null) {
            reload();
        }
    }

    private void reload() {
    }
}