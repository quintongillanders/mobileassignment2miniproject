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
import java.util.List;

public class UpcomingQuizActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    UpcomingTournamentAdapter adapter;
    List<TournamentItem> upComingList;

    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_upcoming_quiz);

        btnBack = findViewById(R.id.btnBack);
        recyclerView = findViewById(R.id.tournamentlistUpcoming);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TournamentManager manager = TournamentManager.getInstance();
        manager.loadTournaments(this);
        manager.updateTournamentLists();

        upComingList = manager.getUpcomingTournaments();
        adapter = new UpcomingTournamentAdapter(this,upComingList);
        recyclerView.setAdapter(adapter);

        if (upComingList.isEmpty()) {
            Toast.makeText(this, "No upcoming tournaments, check back later!", Toast.LENGTH_SHORT).show();
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpcomingQuizActivity.this, QuizHomeScreenActivity.class);
                startActivity(intent);
                Toast.makeText(UpcomingQuizActivity.this, "Back button clicked", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
