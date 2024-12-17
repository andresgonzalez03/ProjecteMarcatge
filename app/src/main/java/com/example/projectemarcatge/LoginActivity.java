package com.example.projectemarcatge;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvForgotPassword;

    private static final String[][] users = {
            {"admin", "1234", "admin"},
            {"professor", "1234", "professor"},
            {"alumne", "1234", "alumne"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                if (isValidUser(username, password)) {
                    String role = getRole(username);
                    navigateToHomePage(role);
                } else {
                    Toast.makeText(LoginActivity.this, "Usuari o contrasenya incorrectes", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Funcionalitat no implementada", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean isValidUser(String username, String password) {
        for (String[] user : users) {
            if (user[0].equals(username) && user[1].equals(password)) {
                return true;
            }
        }
        return false;
    }
    private String getRole(String username) {
        for (String[] user : users) {
            if (user[0].equals(username)) {
                return user[2];
            }
        }
        return "";
    }
    private void navigateToHomePage(String role) {
        Intent intent;
        switch (role) {
            case "admin":
                intent = new Intent(LoginActivity.this, AdminActivity.class);
                break;
            case "professor":
                intent = new Intent(LoginActivity.this, ProfessorActivity.class);
                break;
            case "alumne":
                intent = new Intent(LoginActivity.this, AlumneActivity.class);
                break;
            default:
                intent = new Intent(LoginActivity.this, LoginActivity.class);
                break;
        }
        startActivity(intent);
        finish();
    }
}
