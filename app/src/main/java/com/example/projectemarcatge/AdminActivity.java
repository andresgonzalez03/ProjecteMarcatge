package com.example.projectemarcatge;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {
    private EditText userNameInput, passwordInput;
    private Spinner roleSpinner, userSpinner;
    private Button addUserButton, removeUserButton, passarLlista;

    private List<String> users;
    private ArrayAdapter<String> userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        userNameInput = findViewById(R.id.userNameInput);
        passwordInput = findViewById(R.id.passwordInput);
        roleSpinner = findViewById(R.id.roleSpinner);
        userSpinner = findViewById(R.id.userSpinner);
        addUserButton = findViewById(R.id.addUserButton);
        removeUserButton = findViewById(R.id.removeUserButton);
        passarLlista = findViewById(R.id.passarLlista);

        users = new ArrayList<>();

        ArrayAdapter<CharSequence> roleAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.roles_array,
                android.R.layout.simple_spinner_item
        );
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(roleAdapter);

        userAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, users);
        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(userAdapter);

        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });

        removeUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeUser();
            }
        });
        passarLlista.setOnClickListener(view -> {
            Intent intent = new Intent(this, ProfessorActivity.class);
            startActivity(intent);
        });
    }

    private void addUser() {
        String userName = userNameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String role = roleSpinner.getSelectedItem().toString();

        if (userName.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Si us plau, completa tots els camps.", Toast.LENGTH_SHORT).show();
            return;
        }
        users.add(userName + " (" + role + ")");
        userAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Usuari afegit correctament.", Toast.LENGTH_SHORT).show();

        userNameInput.setText("");
        passwordInput.setText("");
        roleSpinner.setSelection(0);
    }
    private void removeUser() {
        int selectedPosition = userSpinner.getSelectedItemPosition();
        if (selectedPosition >= 0) {
            String removedUser = users.remove(selectedPosition);
            userAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Usuari eliminat: " + removedUser, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Si us plau, sel·lecciona un usuari per esborrar.", Toast.LENGTH_SHORT).show();
        }
    }
}
