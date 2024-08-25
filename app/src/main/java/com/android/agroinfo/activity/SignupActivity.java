package com.android.agroinfo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.agroinfo.utilities.DatabaseHelper;
import com.android.agroinfo.R;

public class SignupActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextEmail, editTextPassword, editRetypeTextPassword;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        editTextUsername = findViewById(R.id.regUsername);
        editTextEmail = findViewById(R.id.regEmail);
        editTextPassword = findViewById(R.id.regPassword);
        editRetypeTextPassword = findViewById(R.id.RegPasswordRetype);
        Button buttonSignup = findViewById(R.id.btnRegister);
        TextView txtLogin = findViewById(R.id.txtLogin);

        databaseHelper = new DatabaseHelper(this);

        buttonSignup.setOnClickListener(view -> {
            String username = editTextUsername.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String retypePassword = editRetypeTextPassword.getText().toString().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || retypePassword.isEmpty()) {
                Toast.makeText(SignupActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(retypePassword)) {
                Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                if (databaseHelper.checkUserEmail(email)) {
                    Toast.makeText(SignupActivity.this, "Email already exists", Toast.LENGTH_SHORT).show();
                } else {
                    boolean result = databaseHelper.insertUser(username, email, password);
                    if (result) {
                        Toast.makeText(SignupActivity.this, "Signup successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SignupActivity.this, "Signup failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        txtLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
