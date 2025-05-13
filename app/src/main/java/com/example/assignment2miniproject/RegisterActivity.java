package com.example.assignment2miniproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    Button btnRegister, btnBack;
    EditText edtEmail, edtPass;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnRegister = findViewById(R.id.btnRegister);
        btnBack = findViewById(R.id.btnBack);
        edtEmail = findViewById(R.id.email_register);
        edtPass = findViewById(R.id.pass_register);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String password = edtPass.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener(authResult -> {
                            String uid = firebaseAuth.getUid();
                            Map<String, Object> user = new HashMap<>();
                            user.put("email", email);
                            String role;

                            if(email.endsWith("@quizcentral")) {
                                role =  "admin";
                            } else {
                                role = "user";

                            }

                            db.collection("Users")
                                    .document(uid)
                                    .set(user)
                                    .addOnSuccessListener(unused -> {
                                        Toast.makeText(RegisterActivity.this, "Registration Success!", Toast.LENGTH_SHORT).show(); // Successfully registered new user
                                        clearFields();

                                        if (role.equals("admin")) {
                                            startActivity(new Intent(RegisterActivity.this, AdminActivity.class));
                                        } else {
                                            startActivity(new Intent(RegisterActivity.this,QuizHomeScreenActivity.class));
                                        }
                                        startActivity(new Intent(RegisterActivity.this, QuizHomeScreenActivity.class));
                                        finish();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(RegisterActivity.this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(RegisterActivity.this, "Authentication failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Toast.makeText(RegisterActivity.this, "Back button clicked!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearFields() {
        edtEmail.setText("");
        edtPass.setText("");
    }
}
