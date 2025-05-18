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

public class DeleteQuizActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DeleteQuizAdapter adapter;
    List<TournamentItem> allTournaments;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete_quiz);

        btnBack = findViewById(R.id.btnBack);
        recyclerView = findViewById(R.id.tournamentlistDelete);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TournamentManager manager = TournamentManager.getInstance();
        manager.loadTournaments(this);
        manager.updateTournamentLists();

        allTournaments = new ArrayList<>();
        allTournaments.addAll(manager.getPastTournaments());

        if (allTournaments.isEmpty()) {
            Toast.makeText(this, "No past tournaments to delete", Toast.LENGTH_SHORT).show();
        }

        adapter = new DeleteQuizAdapter(this, allTournaments);
        recyclerView.setAdapter(adapter);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeleteQuizActivity.this, AdminActivity.class);
                startActivity(intent);
                Toast.makeText(DeleteQuizActivity.this, "Back button clicked", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}

