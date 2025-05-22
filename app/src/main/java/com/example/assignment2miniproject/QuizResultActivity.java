package com.example.assignment2miniproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class QuizResultActivity extends AppCompatActivity {

    private TextView tvResult;
    private Button btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz_result);

        tvResult = findViewById(R.id.txtResult);
        btnHome = findViewById(R.id.btnHome);

        int score = getIntent().getIntExtra("score", 0);

        tvResult.setText("Final Score: " + score + " / 10");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizResultActivity.this, QuizHomeScreenActivity.class);
                startActivity(intent);
                Toast.makeText(QuizResultActivity.this, "Thank you for playing!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}

