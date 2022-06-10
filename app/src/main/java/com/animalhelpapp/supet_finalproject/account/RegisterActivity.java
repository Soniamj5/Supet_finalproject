package com.animalhelpapp.supet_finalproject.account;

import android.app.ProgressDialog;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    Button reg_btn;
    EditText name, petName, email, password, password2;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Inicializar Firebase
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        /*ir a LoginActivity*/
        TextView reg_login = findViewById(R.id.reg_login);
        reg_login.setOnClickListener(v -> iraLogin());

        //conectar con la parte gráfica
        name = findViewById(R.id.reg_name);
        petName = findViewById(R.id.reg_petName);
        email = findViewById(R.id.reg_email);
        password = findViewById(R.id.reg_pass1);
        password2 = findViewById(R.id.reg_pass2);
        progressDialog = new ProgressDialog(RegisterActivity.this);

        //Botón registrar
        reg_btn = findViewById(R.id.reg_btn);
        reg_btn.setOnClickListener(v -> {
            //leer los campos
            String nombre = name.getText().toString().trim();
            String nombreMascota = petName.getText().toString().trim();
            String mail = email.getText().toString().trim();
            String pass = password.getText().toString().trim();
            String pass2 = password2.getText().toString().trim();

            /*error si campos vacíos*/
            if (nombre.isEmpty() || nombreMascota.isEmpty() || mail.isEmpty() || pass.isEmpty() || pass2.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
            } else if (!pass2.equals(pass)) {
                Toast.makeText(RegisterActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                password.requestFocus();
                password2.requestFocus();
            } else if(!mail.matches(emailPattern)) {
                Toast.makeText(RegisterActivity.this, "Email no válido", Toast.LENGTH_SHORT).show();
                email.requestFocus();
            } else {
                /*mostrar que se está registrando*/
                progressDialog.setMessage("Registro en proceso...");
                progressDialog.setTitle("Registro");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                /*método para registrar*/
                registrarUsuario(nombre, nombreMascota, mail, pass);
            }
        });
    }

    //método registrarUsuario
    private void registrarUsuario(String nombre, String nombreMascota, String mail, String pass) {
        /*crear usuario*/
        mAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                progressDialog.dismiss();
                String uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                Map<String, Object> map1 = new HashMap<>();
                map1.put("name", nombre);
                map1.put("pet", nombreMascota);
                map1.put("email", mail);
                map1.put("password", pass);

                /*guardar los datos en collection Firestore*/
                firebaseFirestore.collection("user").document(uid).set(map1).addOnSuccessListener(unused -> {
                    finish();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    Toast.makeText(RegisterActivity.this, "Usuario registrado con éxito!", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> Toast.makeText(RegisterActivity.this, "Fallo al guardar los datos", Toast.LENGTH_SHORT).show());
            } else {
                Toast.makeText(RegisterActivity.this, "Error al registrar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*método iraLogin()*/
    private void iraLogin() {
        finish();
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
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