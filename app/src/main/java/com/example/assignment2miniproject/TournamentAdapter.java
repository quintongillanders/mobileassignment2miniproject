package com.example.assignment2miniproject;


import android.content.Context;
import android.content.Intent;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;

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

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.categoryTextView.setText(Html.fromHtml(category, Html.FROM_HTML_MODE_LEGACY));
        } else {
            holder.categoryTextView.setText(quiz.getCategory());
        }

        holder.difficultyTextView.setText(quiz.getDifficulty() != null ? quiz.getDifficulty(): "");

        if (quiz.getEndDate() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            holder.endDate.setText("Ends on: " + sdf.format(quiz.getEndDate()));
        } else {
            holder.endDate.setText("End date not avalilable");
        }

        holder.beginTournamentButton.setOnClickListener(v -> {
           TournamentItem tournament = tournamentList.get(position);

           OpenTDBService service = RetrofitClient.getService();

           Call<OpenTDBResponse> call = service.getQuestions(
                   10,
                   tournament.getCategoryId(),
                   tournament.getDifficulty(),
                   null
        );

           call.enqueue(new retrofit2.Callback<OpenTDBResponse>() {
               @Override
               public void onResponse(Call<OpenTDBResponse> call, retrofit2.Response<OpenTDBResponse> response) {
                   if (response.isSuccessful() && response.body() != null){
                       List<Question> questions = response.body().getResults();
                       saveTournamentWithQuestions(tournament, questions);

                       // todo: navigate to quiz screen
                       Intent intent = new Intent(context, PlayQuizActivity.class);
                       intent.putExtra("tournamentName", tournament.getCategory());
                       context.startActivity(intent);
                   } else {
                       Toast.makeText(context, "Error getting questions", Toast.LENGTH_SHORT).show();
                       }
                   }

                   @Override
                public void onFailure(Call<OpenTDBResponse> call, Throwable t) {
                   Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                   }
               });
           });
    }

    private void saveTournamentWithQuestions(TournamentItem tournament, List<Question> questions) {
        QuizDataHolder.getInstance().setQuestions(questions);
    }

    @Override
    public int getItemCount() {
        return tournamentList.size();
    }

    public static class TournamentViewHolder extends RecyclerView.ViewHolder {
        TextView categoryTextView;
        TextView difficultyTextView;

        TextView endDate;
        Button beginTournamentButton;

        public TournamentViewHolder(View itemView) {
            super(itemView);
            categoryTextView = itemView.findViewById(R.id.txtCategory);
            difficultyTextView = itemView.findViewById(R.id.txtDifficulty);

            endDate = itemView.findViewById(R.id.txtEndDate);
            beginTournamentButton = itemView.findViewById(R.id.btnBegin);
        }
    }
}


