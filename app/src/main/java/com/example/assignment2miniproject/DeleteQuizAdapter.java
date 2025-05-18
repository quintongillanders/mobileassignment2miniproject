package com.example.assignment2miniproject;


import android.app.AlertDialog;
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

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class DeleteQuizAdapter extends RecyclerView.Adapter<DeleteQuizAdapter.DeleteQuizViewHolder> {
    private Context context;
    private List<TournamentItem> quizList;

    public DeleteQuizAdapter(Context context, List<TournamentItem> quizList) {
        this.context = context;
        this.quizList = quizList;
    }
    @NonNull
    @Override
    public DeleteQuizViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.delete_quiz_item, parent, false);
        return new DeleteQuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DeleteQuizViewHolder holder, int position) {
        TournamentItem quiz = quizList.get(position);

        String category = quiz.getCategory() != null ? quiz.getCategory() : "";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.categoryTextView.setText(Html.fromHtml(category, Html.FROM_HTML_MODE_LEGACY));
        } else {
            holder.categoryTextView.setText(quiz.getCategory());

        }

        holder.txtLikes.setText("❤️: " + quiz.getLikeCount());
        holder.txtDislikes.setText("💔: " + quiz.getDislikeCount());

        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Confirm Deletion")
                    .setMessage("Are you sure you want to delete this tournament? This cannot be undone.")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        TournamentItem tournamentToDelete = quizList.get(position);

                        TournamentManager.getInstance().deleteTournament(tournamentToDelete, context);

                        quizList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, quizList.size());
                        Toast.makeText(context, "Tournament deleted!", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> {
                        dialog.dismiss();
                    })
                            .create()
                            .show();
        });
    }


    @Override
    public int getItemCount() {
        return quizList.size();
    }

    public static class DeleteQuizViewHolder extends RecyclerView.ViewHolder {
        TextView categoryTextView, txtLikes, txtDislikes;
        Button btnDelete;

        public DeleteQuizViewHolder(View itemView) {
            super(itemView);
            categoryTextView = itemView.findViewById(R.id.txtCategory);
            txtLikes = itemView.findViewById(R.id.txtLike);
            txtDislikes = itemView.findViewById(R.id.txtDislike);
            btnDelete = itemView.findViewById(R.id.btnDelete);

        }
    }
}



