package com.example.assignment2miniproject;


import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.text.Html;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TournamentAdapter extends RecyclerView.Adapter<TournamentAdapter.TournamentViewHolder> {
    private Context context;
    private List<TournamentItem> tournamentList;

    public TournamentAdapter(Context context, List<TournamentItem> tournamentList) {
        this.context = context;
        this.tournamentList = tournamentList;
    }

    @NonNull
    @Override
    public TournamentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.tournament_item, parent, false);
        return new TournamentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TournamentViewHolder holder, int position) {
        TournamentItem quiz = tournamentList.get(position);

        String category = quiz.getCategory() != null ? quiz.getCategory() : "";
        String sampleQuestion = quiz.getSampleQuestion() != null ? quiz.getSampleQuestion() : "";

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.categoryTextView.setText(Html.fromHtml(category, Html.FROM_HTML_MODE_LEGACY));
            holder.sampleQuestionTextView.setText(Html.fromHtml(sampleQuestion, Html.FROM_HTML_MODE_LEGACY));
        } else {
            holder.categoryTextView.setText(quiz.getCategory());
            holder.sampleQuestionTextView.setText(quiz.getSampleQuestion());
        }

        holder.difficultyTextView.setText(quiz.getDifficulty() != null ? quiz.getDifficulty(): "");

        holder.beginTournamentButton.setOnClickListener(v -> {
           // todo: handle begin tournament
        });
    }

    @Override
    public int getItemCount() {
        return tournamentList.size();
    }

    public static class TournamentViewHolder extends RecyclerView.ViewHolder {
        TextView categoryTextView;
        TextView difficultyTextView;
        TextView sampleQuestionTextView;
        Button beginTournamentButton;

        public TournamentViewHolder(View itemView) {
            super(itemView);
            categoryTextView = itemView.findViewById(R.id.txtCategory);
            difficultyTextView = itemView.findViewById(R.id.txtDifficulty);
            sampleQuestionTextView = itemView.findViewById(R.id.txtSampleQuestion);
            beginTournamentButton = itemView.findViewById(R.id.btnBegin);
        }
    }
}


