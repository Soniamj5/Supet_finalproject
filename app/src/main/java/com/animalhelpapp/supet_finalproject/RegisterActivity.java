package com.animalhelpapp.supet_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    Button reg_btn;
    EditText name, email, password, password2;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Inicializar Firebase
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        /*ir a LoginActivity*/
        TextView reg_login = findViewById(R.id.reg_login);
        reg_login.setOnClickListener(v -> iraLogin());

        //conectar con la parte gráfica
        name = findViewById(R.id.reg_name);
        email = findViewById(R.id.reg_email);
        password = findViewById(R.id.reg_pass1);
        password2 = findViewById(R.id.reg_pass2);

        //Botón registrar
        reg_btn = findViewById(R.id.reg_btn);
        reg_btn.setOnClickListener(v -> {
            //leer los campos
            String nombre = name.getText().toString().trim();
            String mail = email.getText().toString().trim();
            String pass = password.getText().toString().trim();
            String pass2 = password2.getText().toString().trim();

            /*error si campos vacíos*/
            if (nombre.isEmpty() && mail.isEmpty() && pass.isEmpty() && pass2.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
            } else if (!pass2.equals(pass)) {
                Toast.makeText(RegisterActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            } else {
                registrarUsuario(nombre, mail, pass);
            }
        });
    }

    //método registrarUsuario
    private void registrarUsuario(String nombre, String mail, String pass) {
        /*crear usuario*/
        mAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(task -> {
            String id = mAuth.getCurrentUser().getUid();
            Map<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("name", nombre);
            map.put("email", mail);
            map.put("password", pass);
            /*guardar los datos en collection Firestore*/
            mFirestore.collection("user").document(id).set(map).addOnSuccessListener(unused -> {
                /*si registro con éxito, ir a MainActivity*/
                finish();
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                Toast.makeText(RegisterActivity.this, "Usuario registrado con éxito!", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(e -> Toast.makeText(RegisterActivity.this, "Error al guardar los datos!", Toast.LENGTH_SHORT).show());

        }).addOnFailureListener(e -> Toast.makeText(RegisterActivity.this, "Error al registrar!", Toast.LENGTH_SHORT).show());
    }

    /*método iraLogin()*/
    private void iraLogin() {
        finish();
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }
}