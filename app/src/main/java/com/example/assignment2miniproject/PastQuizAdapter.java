package com.example.assignment2miniproject;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.text.Html;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PastQuizAdapter extends RecyclerView.Adapter<PastQuizAdapter.PastQuizViewHolder> {
    private Context context;
    private List<TournamentItem> quizList;

    private static final String PREFS_NAME = "QuizLikePrefs";

    public PastQuizAdapter(Context context, List<TournamentItem> quizList) {
        this.context = context;
        this.quizList = quizList;
    }
    @NonNull
    @Override
    public PastQuizViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.past_item, parent, false);
        return new PastQuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PastQuizViewHolder holder, int position) {
        TournamentItem quiz = quizList.get(position);

        String category = quiz.getCategory() != null ? quiz.getCategory() : "";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.categoryTextView.setText(Html.fromHtml(category, Html.FROM_HTML_MODE_LEGACY));
        } else {
            holder.categoryTextView.setText(quiz.getCategory());

        }

        if (quiz.getEndDate() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            holder.endDate.setText("Ended on: " + sdf.format(quiz.getEndDate()));
        } else {
            holder.endDate.setText("End date not avalilable");
        }

        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        int savedLikes = prefs.getInt("likes_" + quiz.getId(), quiz.getLikeCount());
        int savedDislikes = prefs.getInt("dislikes_" + quiz.getId(), quiz.getDislikeCount());

        quiz.setLikeCount(savedLikes);
        quiz.setDislikeCount(savedDislikes);

        holder.btnLike.setText("👍 (" + quiz.getLikeCount() + ")");
        holder.btnDislike.setText("👎 (" + quiz.getDislikeCount() + ")");

        holder.btnLike.setOnClickListener(v -> {
            int newLikes = quiz.getLikeCount() + 1;
            quiz.setLikeCount(newLikes);
            prefs.edit().putInt("likes_" + quiz.getId(), newLikes).apply();
            holder.btnLike.setText("👍 (" + quiz.getLikeCount() + ")");
            Toast.makeText(context, "Thank you for your feedback!", Toast.LENGTH_SHORT).show();
        });


        holder.btnDislike.setOnClickListener(v -> {
            int newDislikes = quiz.getDislikeCount() + 1;
            quiz.setDislikeCount(newDislikes);
            prefs.edit().putInt("dislikes_" + quiz.getId(), newDislikes).apply();
            holder.btnDislike.setText("👎 (" + quiz.getDislikeCount() + ")");
            Toast.makeText(context, "Thank you for your feedback!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return quizList.size();
    }
    public static class PastQuizViewHolder extends RecyclerView.ViewHolder {
        TextView categoryTextView;
        TextView endDate;
        Button btnLike, btnDislike;

        public PastQuizViewHolder(View itemView) {
            super(itemView);
            categoryTextView = itemView.findViewById(R.id.txtCategory);
            btnDislike = itemView.findViewById(R.id.btnDislike);
            btnLike = itemView.findViewById(R.id.btnLike);
            endDate = itemView.findViewById(R.id.txtEndDate);

        }
    }
}