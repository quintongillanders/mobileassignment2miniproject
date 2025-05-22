package com.example.assignment2miniproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Collections;
import java.util.List;

public class PlayQuizActivity extends AppCompatActivity {

    private TextView txtQuestion, txtTournamentName;
    private Button[] optionButtons = new Button[4];
    private Button btnNext;

    private List<Question> questionList;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private boolean answered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_play_quiz);


        txtQuestion = findViewById(R.id.txtQuestion);
        txtTournamentName = findViewById(R.id.txtTournamentName);
        optionButtons[0] = findViewById(R.id.btnOp1);
        optionButtons[1] = findViewById(R.id.btnOp2);
        optionButtons[2] = findViewById(R.id.btnOp3);
        optionButtons[3] = findViewById(R.id.btnOp4);
        btnNext = findViewById(R.id.btnNext);

        questionList = QuizDataHolder.getInstance().getQuestions();

        if (questionList == null || questionList.isEmpty()) {
            Toast.makeText(this, "No questions available", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String tournamentName = getIntent().getStringExtra("tournamentName");
        if (tournamentName != null) {
            txtTournamentName.setText(tournamentName);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        showQuestion();

        for (Button btn : optionButtons) {
            btn.setOnClickListener(v -> checkAnswer((Button) v));
        }

        btnNext.setOnClickListener(v -> {
            currentQuestionIndex++;
            if (currentQuestionIndex < questionList.size()) {
                showQuestion();
            } else {
                Intent intent = new Intent(PlayQuizActivity.this, QuizResultActivity.class);
                intent.putExtra("score", score);
                startActivity(intent);
                finish();
            }
        });
    }

        private void showQuestion() {
            answered = false;
            btnNext.setVisibility(View.GONE);

            for (Button btn : optionButtons) {
                btn.setEnabled(true);
                btn.setBackgroundColor(Color.parseColor("#05FAE4"));
                btn.setVisibility(View.VISIBLE);

            }

            Question q = questionList.get(currentQuestionIndex);
            txtQuestion.setText(Html.fromHtml(q.getQuestionText(), Html.FROM_HTML_MODE_LEGACY));

            List<String> answers = q.getAllAnswersShuffled();
            int numAnswers = answers.size();

            for (int i = 0; i < optionButtons.length; i++) {
                if (i < numAnswers) {
                    optionButtons[i].setText(Html.fromHtml(answers.get(i), Html.FROM_HTML_MODE_LEGACY));
                    optionButtons[i].setVisibility(View.VISIBLE);
                } else {
                    optionButtons[i].setVisibility(View.GONE);
                }
            }

            if (currentQuestionIndex == questionList.size() - 1) {
                btnNext.setText("Finish Tournament");
            } else {
                btnNext.setText("Next");
            }
        }

        private void checkAnswer(Button selectedButton) {
    if (answered) return;

    answered = true;

    String selected = selectedButton.getText().toString();
    String correct = questionList.get(currentQuestionIndex).getCorrectAnswer();

    if (Html.fromHtml(selected, Html.FROM_HTML_MODE_LEGACY).toString().equals(
            Html.fromHtml(correct, Html.FROM_HTML_MODE_LEGACY).toString())) {
        selectedButton.setBackgroundColor(Color.GREEN);
        score++;
        Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();

    } else {
        selectedButton.setBackgroundColor(Color.RED);
        Toast.makeText(this, "Incorrect Answer! Correct Answer is: " + correct, Toast.LENGTH_LONG).show();

        for (Button btn: optionButtons) {
            if (btn.getText().toString().equals(correct)) {
                btn.setBackgroundColor(Color.GREEN);
                break;
            }
        }
    }

    for (Button btn : optionButtons) {
        btn.setEnabled(false);
    }

        btnNext.setVisibility(View.VISIBLE);

    }
}

