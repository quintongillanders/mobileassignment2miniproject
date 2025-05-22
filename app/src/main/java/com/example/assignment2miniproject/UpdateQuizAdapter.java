package com.example.assignment2miniproject;

import android.app.AlertDialog;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class UpdateQuizAdapter extends RecyclerView.Adapter<UpdateQuizAdapter.ViewHolder> {

    Context context;
    List<TournamentItem> tournamentList;

    public UpdateQuizAdapter(Context context, List<TournamentItem> tournamentList) {
        this.context = context;
        this.tournamentList = tournamentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.update_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TournamentItem tournament = tournamentList.get(position);
        holder.txtCategory.setText(tournament.getCategory());
        holder.btnUpdate.setOnClickListener(v -> showUpdateDialog(tournament, position));
    }

    private void showUpdateDialog(TournamentItem tournament, int position) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_update_quiz, null);
        EditText editCategory = dialogView.findViewById(R.id.editCategory);

        editCategory.setText(tournament.getCategory());

        new AlertDialog.Builder(context)
                .setTitle("Update tournament")
                .setView(dialogView)
                .setPositiveButton("Update", (dialog, which) -> {
                    String newCategory = editCategory.getText().toString().trim();

                    new AlertDialog.Builder(context)
                            .setTitle("Confirm update")
                            .setMessage("Are you sure you want to update this tournament category?")
                            .setPositiveButton("Yes", (confirmDialog, confirmWhich) -> {
                                tournament.setCategory(newCategory);
                                notifyItemChanged(position);
                                Toast.makeText(context, "Tournament updated", Toast.LENGTH_SHORT).show();
                            })
                            .setNegativeButton("Cancel", null)
                            .show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public int getItemCount() {
        return tournamentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCategory;
        Button btnUpdate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCategory = itemView.findViewById(R.id.txtCategory);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
        }
    }
}
