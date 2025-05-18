package com.example.assignment2miniproject;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.text.Html;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Calendar;
import java.util.Date;

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

        String category = quiz.getCategory() != null ? quiz.getCategory() : "";

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.categoryTextView.setText(Html.fromHtml(category, Html.FROM_HTML_MODE_LEGACY));
        } else {
            holder.categoryTextView.setText(quiz.getCategory());

        }

        holder.difficultyTextView.setText(quiz.getDifficulty() != null ? quiz.getDifficulty() : "");

        holder.createTournamentButton.setOnClickListener(v -> {
            Calendar currentCal = Calendar.getInstance();

            DatePickerDialog startDatePicker = new DatePickerDialog(context,
                    (DatePicker view, int startYear, int startMonth, int startDay) -> {
                        Calendar startCal = Calendar.getInstance();
                        startCal.set(startYear, startMonth, startDay);

                        DatePickerDialog endDatePicker = new DatePickerDialog(context,
                                (DatePicker view2, int endYear, int endMonth, int endDay) -> {
                                    Calendar endCal = Calendar.getInstance();
                                    endCal.set(endYear, endMonth, endDay);

                                    if (endCal.before(startCal)) {
                                        Toast.makeText(context, "End date needs to be set after the start date", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    TournamentItem tournament = new TournamentItem(
                                            category,
                                            quiz.getDifficulty(),
                                            startCal.getTime(),
                                            endCal.getTime()
                                    );

                                    TournamentManager.getInstance().addTournament(tournament, v.getContext());

                                    Toast.makeText(context, "Tournament created!", Toast.LENGTH_SHORT).show();
                                },
                                currentCal.get(Calendar.YEAR),
                                currentCal.get(Calendar.MONTH),
                                currentCal.get (Calendar.DAY_OF_MONTH)
                        );

                        endDatePicker.setTitle("Select end date");
                        endDatePicker.show();

                    },
                    currentCal.get(Calendar.YEAR),
                    currentCal.get(Calendar.MONTH),
                    currentCal.get(Calendar.DAY_OF_MONTH)
        );

            startDatePicker.setTitle("Select start date");
            startDatePicker.show();
        });
    }

    @Override
    public int getItemCount() {
        return quizList.size();
    }

    public static class QuizViewHolder extends RecyclerView.ViewHolder {
        TextView categoryTextView;
        TextView difficultyTextView;

        Button createTournamentButton;

        public QuizViewHolder(View itemView) {
            super(itemView);
            categoryTextView = itemView.findViewById(R.id.txtCategory);
            difficultyTextView = itemView.findViewById(R.id.txtDifficulty);
            createTournamentButton = itemView.findViewById(R.id.btnCreate);
        }
    }
}


