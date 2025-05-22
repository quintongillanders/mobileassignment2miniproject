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

public class UpdateQuizActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    UpdateQuizAdapter adapter;
    List<TournamentItem> updateList;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.tournamentlistUpdate);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnBack = findViewById(R.id.btnBack);

        TournamentManager manager = TournamentManager.getInstance();
        manager.loadTournaments(this);
        manager.updateTournamentLists();

        updateList = new ArrayList<>();
        updateList.addAll(manager.getUpcomingTournaments());
        updateList.addAll(manager.getOngoingTournaments());

        adapter = new UpdateQuizAdapter(this, updateList);
        recyclerView.setAdapter(adapter);

        if (updateList.isEmpty()) {
            Toast.makeText(this, "No tournaments available to update at the moment", Toast.LENGTH_SHORT).show();
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateQuizActivity.this, AdminActivity.class);
                startActivity(intent);
                Toast.makeText(UpdateQuizActivity.this, "Back button clicked", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
