package com.example.assignment2miniproject;


import android.content.Context;
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


        holder.btnLike.setOnClickListener(v -> {
            String quizId = quiz.getId();
            if (quizId == null || quizId.isEmpty()) {
                Toast.makeText(context, "Quiz ID not found, cannot like quiz", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(context, "Tournament Liked!", Toast.LENGTH_SHORT).show();
            updateLikeCount(quiz.getId(), true); //  true: liked
        });

        holder.btnDislike.setOnClickListener(v -> {
            String quizId = quiz.getId();
            if (quizId == null || quizId.isEmpty()) {
                Toast.makeText(context, "Quiz ID not found, cannot dislike quiz", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(context, "Tournament Disliked!", Toast.LENGTH_SHORT).show();
            updateLikeCount(quiz.getId(), true); //  true: liked
        });
    }
    private void updateLikeCount(String quizId, boolean isLike) {
        DatabaseReference quizRef = FirebaseDatabase.getInstance()
                .getReference("tournaments")
                .child(quizId);

        quizRef.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                TournamentItem currentQuiz = currentData.getValue(TournamentItem.class);
                if (currentQuiz == null) return Transaction.success(currentData);

                if (isLike) {
                    currentQuiz.setLikeCount(currentQuiz.getLikeCount() + 1);
                } else {
                    currentQuiz.setDislikeCount(currentQuiz.getDislikeCount() + 1);
                }
                currentData.setValue(currentQuiz);
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                if (error != null) {
                    Toast.makeText(context, "Failed to update, Please try again later", Toast.LENGTH_SHORT).show();
                }
            }
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