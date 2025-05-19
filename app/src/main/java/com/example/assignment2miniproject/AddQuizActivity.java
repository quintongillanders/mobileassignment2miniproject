package com.example.assignment2miniproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddQuizActivity extends AppCompatActivity {
    Button btnBack;
    private RecyclerView recyclerView;
    private QuizAdapter quizAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_quiz);

        recyclerView = findViewById(R.id.quizlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnBack = findViewById(R.id.btnBack);

        fetchQuizQuestions();
    }

    private void fetchQuizQuestions() {
        OpenTDBService api = RetrofitClient.getService();
        Call<OpenTDBResponse> call = api.getQuestions(
                20,
                null,
                null,
                null
        );

        call.enqueue(new Callback<OpenTDBResponse>() {
            @Override
            public void onResponse(Call<OpenTDBResponse> call, Response<OpenTDBResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Question> questions = response.body().getResults();

                    List<QuizItem> quizItems = QuizItem.fromQuestions(questions);
                    saveQuizzesToFireBase(quizItems);

                    quizAdapter = new QuizAdapter(AddQuizActivity.this, quizItems);
                    recyclerView.setAdapter(quizAdapter);
                } else {
                    Toast.makeText(AddQuizActivity.this, "Failed to find quiz data", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<OpenTDBResponse> call, Throwable t) {
                Toast.makeText(AddQuizActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("QUIZ_API", "Failure: ", t);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddQuizActivity.this, AdminActivity.class);
                startActivity(intent);
                Toast.makeText(AddQuizActivity.this, "Back button clicked", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void saveQuizzesToFireBase(List<QuizItem> quizItems) {
        DatabaseReference tournamentsRef = FirebaseDatabase.getInstance().getReference("tournaments");

        for (QuizItem quiz : quizItems) {
            String newId = tournamentsRef.push().getKey();
            quiz.setId(newId);

            TournamentItem tournamentItem = new TournamentItem(
                    quiz.getCategory(),
                    quiz.getDifficulty(),
                    null,
                    null
            );
        }
    }
}


