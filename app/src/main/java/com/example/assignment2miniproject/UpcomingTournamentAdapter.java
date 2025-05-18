package com.example.assignment2miniproject;


import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.text.Html;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class UpcomingTournamentAdapter extends RecyclerView.Adapter<UpcomingTournamentAdapter.UpcomingTournamentViewHolder> {
    private Context context;
    private List<TournamentItem> tournamentList;

    public UpcomingTournamentAdapter(Context context, List<TournamentItem> tournamentList) {
        this.context = context;
        this.tournamentList = tournamentList;
    }

    @NonNull
    @Override
    public UpcomingTournamentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.upcoming_item, parent, false);
        return new UpcomingTournamentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UpcomingTournamentViewHolder holder, int position) {
        TournamentItem quiz = tournamentList.get(position);

        String category = quiz.getCategory() != null ? quiz.getCategory() : "";

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.categoryTextView.setText(Html.fromHtml(category, Html.FROM_HTML_MODE_LEGACY));
        } else {
            holder.categoryTextView.setText(quiz.getCategory());

        }

        holder.difficultyTextView.setText(quiz.getDifficulty() != null ? quiz.getDifficulty(): "");

        if(quiz.getStartDate() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            holder.startDateTextView.setText("Starts on: " + sdf.format(quiz.getStartDate()));
        } else {
            holder.startDateTextView.setText("Start date not available");
        }
    }

    @Override
    public int getItemCount() {
        return tournamentList.size();
    }

    public static class UpcomingTournamentViewHolder extends RecyclerView.ViewHolder {
        TextView categoryTextView;
        TextView difficultyTextView;
        TextView startDateTextView;

        public UpcomingTournamentViewHolder(View itemView) {
            super(itemView);
            categoryTextView = itemView.findViewById(R.id.txtCategory);
            difficultyTextView = itemView.findViewById(R.id.txtDifficulty);
            startDateTextView = itemView.findViewById(R.id.txtStartDate);
        }
    }
}


