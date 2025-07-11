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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OngoingQuizActivity extends AppCompatActivity {

    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ongoing_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnBack = findViewById(R.id.btnBack);
        RecyclerView recyclerView = findViewById(R.id.tournamentlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TournamentManager manager = TournamentManager.getInstance();
        manager.loadTournaments(this);
        manager.updateTournamentLists();
        manager.moveEndedTournamentsToPast(this);

        List<TournamentItem> tournaments = manager.getOngoingTournaments();
        TournamentAdapter adapter = new TournamentAdapter(this, tournaments);
        recyclerView.setAdapter(adapter);

        if (tournaments.isEmpty()) {
            Toast.makeText(this, "No ongoing tournaments at this time, check back later!", Toast.LENGTH_SHORT).show();
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OngoingQuizActivity.this, QuizHomeScreenActivity.class);
                startActivity(intent);
                Toast.makeText(OngoingQuizActivity.this, "Back button clicked", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    }



