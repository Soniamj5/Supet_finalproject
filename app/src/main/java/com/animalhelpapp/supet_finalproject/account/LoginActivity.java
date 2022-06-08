package com.animalhelpapp.supet_finalproject.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.animalhelpapp.supet_finalproject.MainActivity;
import com.animalhelpapp.supet_finalproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    Button login_btn;
    EditText email, password;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //inicializar Firebase
        mAuth = FirebaseAuth.getInstance();

        /*Button login*/
        login_btn = findViewById(R.id.login_btn);
        login_btn.setOnClickListener(v -> iniciarSesion());

        //acción ir a RegisterActivity
        TextView login_register = findViewById(R.id.login_register);
        login_register.setOnClickListener(v -> iraRegistrar());
    }

    //método iniciarSesion
    private void iniciarSesion() {
        //conectar parte gráfica
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);

        //leer los campos
        String mail = email.getText().toString().trim();
        String pass = password.getText().toString().trim();

        //error si algún campo está vacio
        if (mail.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    finish();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    Toast.makeText(LoginActivity.this, "Bienvenid@", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(e -> Toast.makeText(LoginActivity.this, "Error al iniciar sesión.", Toast.LENGTH_SHORT).show());
        }
    }

    //método iraRegistrar()
    private void iraRegistrar() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    /*si está iniciada la sesión, ir directamente al MainActivity*/
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }
}