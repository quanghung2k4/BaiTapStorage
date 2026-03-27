package com.learn.baitapstorage.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.learn.baitapstorage.MainActivity;
import com.learn.baitapstorage.R;
import com.learn.baitapstorage.dal.AppDatabase;
import com.learn.baitapstorage.dal.SharedPrefsManager;
import com.learn.baitapstorage.models.User;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUsername, edtPassword;
    private Button btnLogin;
    private TextView btnRegister;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = AppDatabase.getInstance(this);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(v -> {
            String user = edtUsername.getText().toString().trim();
            String pass = edtPassword.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            User account = db.appDao().login(user, pass);
            if (account != null) {
                SharedPrefsManager.saveLogin(this, account.getId());
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });

        btnRegister.setOnClickListener(v -> {
            // Simple registration for demo purposes
            String user = edtUsername.getText().toString().trim();
            String pass = edtPassword.getText().toString().trim();
            
            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Enter username and password to register", Toast.LENGTH_SHORT).show();
                return;
            }
            
            User newUser = new User();
            newUser.setUsername(user);
            newUser.setPassword(pass);
            newUser.setFullName(user);
            
            db.appDao().insertUser(newUser);
            Toast.makeText(this, "Registered successfully! Now login.", Toast.LENGTH_SHORT).show();
        });
    }
}
