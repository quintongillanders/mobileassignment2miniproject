package com.example.assignment2miniproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class QuizHomeScreenActivity extends AppCompatActivity {

Button btnOngoing, btnUpcoming, btnPast, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz_home_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnOngoing = findViewById(R.id.btnOngoing);
        btnUpcoming = findViewById(R.id.btnUpcoming);
        btnPast = findViewById(R.id.btnpast);
        btnLogout = findViewById(R.id.btnLogout);

        btnOngoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizHomeScreenActivity.this, OngoingQuizActivity.class);
                startActivity(intent);
                Toast.makeText(QuizHomeScreenActivity.this, "Ongoing Tournaments Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        btnUpcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizHomeScreenActivity.this, UpcomingQuizActivity.class);
                startActivity(intent);
                Toast.makeText(QuizHomeScreenActivity.this, "Upcoming Tournaments Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        btnPast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizHomeScreenActivity.this, PreviousTournamentActivity.class);
                startActivity(intent);
                Toast.makeText(QuizHomeScreenActivity.this, "Past Tournaments Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizHomeScreenActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Toast.makeText(QuizHomeScreenActivity.this, "Successfully logged out, see you soon!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}