package com.example.assignment2miniproject;

import static android.view.View.inflate;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizViewHolder> {
    private Context context;
    private List<QuizItem> quizList;

    public QuizAdapter(Context context, List<QuizItem> quizList) {
        this.context = context;
        this.quizList = quizList;
    }

    @NonNull
    @Override
    public QuizViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.quiz_item, parent, false);
        return new QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuizViewHolder holder, int position) {
        QuizItem quiz = quizList.get(position);

        holder.categoryTextView.setText(quiz.getCategory());
        holder.difficultyTextView.setText(quiz.getDifficulty());
        holder.sampleQuestionTextView.setText(quiz.getSampleQuestion());

        holder.createTournamentButton.setOnClickListener(v -> {

        });
    }

    @Override
    public int getItemCount() {
        return quizList.size();
    }

    public static class QuizViewHolder extends RecyclerView.ViewHolder {
        TextView categoryTextView;
        TextView difficultyTextView;
        TextView sampleQuestionTextView;
        Button createTournamentButton;

        public QuizViewHolder(View itemView) {
            super(itemView);
            categoryTextView = itemView.findViewById(R.id.txtCategory);
            difficultyTextView = itemView.findViewById(R.id.txtDifficulty);
            sampleQuestionTextView = itemView.findViewById(R.id.txtSampleQuestion);
            createTournamentButton = itemView.findViewById(R.id.btnCreate);
        }
    }
}


