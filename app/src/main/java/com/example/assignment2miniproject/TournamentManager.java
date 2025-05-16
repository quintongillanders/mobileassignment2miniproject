package com.example.assignment2miniproject;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TournamentManager {
    private static TournamentManager instance;
    private final List<TournamentItem> tournaments = new ArrayList<>();

    private TournamentManager() {}
    public static TournamentManager getInstance() {
        if (instance == null) {
            instance = new TournamentManager();
        }
        return instance;
    }

    public void addTournament(TournamentItem item, Context context) {
        tournaments.add(item);
        saveTournaments(context);
    }

    public List<TournamentItem> getTournaments() {
        return tournaments;
    }

    public void saveTournaments(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("tournament_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();
        String json = gson.toJson(tournaments);
        editor.putString("tournaments", json);
        editor.apply();
    }

    public void loadTournaments(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("tournament_prefs", Context.MODE_PRIVATE);
        String json = prefs.getString("tournaments", null);

        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<TournamentItem>>() {}.getType();
            List<TournamentItem> loadedTournaments = gson.fromJson(json, type);
            tournaments.clear();
            tournaments.addAll(loadedTournaments);

        }
    }
}
