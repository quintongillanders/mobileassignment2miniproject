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
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PastQuizAdapter extends RecyclerView.Adapter<PastQuizAdapter.PastQuizViewHolder> {
    private Context context;
    private List<TournamentItem> quizList;
    private Vote voteStore;
    private FirebaseFirestore db;
    private String currentUserId;

    public PastQuizAdapter(Context context, List<TournamentItem> quizList) {
        this.context = context;
        this.quizList = quizList;
        db = FirebaseFirestore.getInstance();
        String currentUserId = getCurrentUserId();
        this.voteStore = new Vote(context, currentUserId);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();;
        currentUserId = (user != null) ? user.getUid() : null;
    }

    private String getCurrentUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return (user != null) ? user.getUid() : "guest";
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

            boolean hasUserVoted = voteStore.hasVoted(quiz.getId());
            quiz.setHasVoted(hasUserVoted);

            if (hasUserVoted) {
                quiz.setLikes(voteStore.getLikes(quiz.getId(), quiz.getLikes()));
                quiz.setDislikes(voteStore.getDislikes(quiz.getId(), quiz.getDislikes()));

                boolean liked = voteStore.isLiked(quiz.getId());

                if (liked) {
                    holder.btnLike.setAlpha(1f);
                    holder.btnDislike.setAlpha(0.5f);
                } else {
                    holder.btnLike.setAlpha(0.5f);
                    holder.btnDislike.setAlpha(1f);
                }

                holder.btnLike.setEnabled(false);
                holder.btnDislike.setEnabled(false);
            } else {
                holder.btnLike.setAlpha(1f);
                holder.btnDislike.setAlpha(1f);
                holder.btnLike.setEnabled(true);
                holder.btnDislike.setEnabled(true);
        }

        holder.btnLike.setText("Like (" + quiz.getLikes() + ")");
        holder.btnDislike.setText("Dislike (" + quiz.getDislikes() + ")");


        holder.btnLike.setEnabled(!quiz.hasVoted());
        holder.btnDislike.setEnabled(!quiz.hasVoted());

        holder.btnLike.setOnClickListener(v -> {
            if (!quiz.hasVoted()) {
                quiz.setLikes(quiz.getLikes() + 1);
                voteStore.saveVoteWithCount(quiz.getId(), true, quiz.getLikes(), quiz.getDislikes());
                quiz.setHasVoted(true);
                notifyItemChanged(position);

                FirebaseFirestore.getInstance()
                                .collection("quizzes")
                                        .document(quiz.getId())
                                                .update("likes", FieldValue.increment(1));

                Toast.makeText(context, "Thank you for your feedback!", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnDislike.setOnClickListener(v -> {
            if (!quiz.hasVoted()) {
                quiz.setDislikes(quiz.getDislikes() + 1);
                voteStore.saveVoteWithCount(quiz.getId(), false, quiz.getLikes(), quiz.getDislikes());
                quiz.setHasVoted(true);
                notifyItemChanged(position);

                FirebaseFirestore.getInstance()
                        .collection("quizzes")
                        .document(quiz.getId())
                        .update("dislikes", FieldValue.increment(1));
                Toast.makeText(context, "Thank you for your feedback!", Toast.LENGTH_SHORT).show();

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