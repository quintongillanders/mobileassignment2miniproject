package com.example.assignment2miniproject;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class TournamentManager {
    private static TournamentManager instance;
    private List<TournamentItem> upcomingTournaments = new ArrayList<>();
    private List<TournamentItem> ongoingTournaments = new ArrayList<>();
    private List<TournamentItem> pastTournaments = new ArrayList<>();
    private final List<TournamentItem> tournaments = new ArrayList<>();

    private TournamentManager() {
    }

    public static TournamentManager getInstance() {
        if (instance == null) {
            instance = new TournamentManager();
        }
        return instance;
    }

    public void addTournament(TournamentItem item, Context context) {
        if (item.getId() == null || item.getId().isEmpty()) {
            item.setId("tournament_" + System.currentTimeMillis());
        }
        tournaments.add(item);
        saveTournaments(context);
    }

    public void deleteTournament(TournamentItem item, Context context) {
        tournaments.remove(item);
        pastTournaments.remove(item);
        saveTournaments(context);
    }

    public List<TournamentItem> getAllTournaments() {
        return tournaments;
    }

    public List<TournamentItem> getUpcomingTournaments() {
        return upcomingTournaments;
    }

    public List<TournamentItem> getOngoingTournaments() {
        return ongoingTournaments;
    }

    public List<TournamentItem> getPastTournaments() {
        return pastTournaments;
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
            Type type = new TypeToken<List<TournamentItem>>() {
            }.getType();
            List<TournamentItem> loadedTournaments = gson.fromJson(json, type);
            tournaments.clear();

            for (TournamentItem t : loadedTournaments) {
                if (t.getId() == null || t.getId().isEmpty()) {
                    t.setId("tournament_" + System.currentTimeMillis());
                }
                tournaments.add(t);

            }
        }
    }


    public void updateTournamentLists() {
        upcomingTournaments.clear();
        ongoingTournaments.clear();
        pastTournaments.clear();

        Date now = new Date();

        for (TournamentItem t : tournaments) {
            Date start = t.getStartDate();
            Date end = t.getEndDate();

            if (start == null) {
                upcomingTournaments.add(t);

            } else if (end == null) {
                if (!start.after(now)) {
                    ongoingTournaments.add(t);

                } else if (end == null) {
                    upcomingTournaments.add(t);
                }
            } else {

                if (end.before(now)) {
                    pastTournaments.add(t);

                } else if (!start.after(now) && !end.before(now)) {
                    ongoingTournaments.add(t);

                } else if (start.after(now)) {
                    upcomingTournaments.add(t);
                }
            }
        }
    }


    public void moveEndedTournamentsToPast(Context context) {
        Date now = new Date();

        Iterator<TournamentItem> iterator = ongoingTournaments.iterator();
        boolean changed = false;

        while (iterator.hasNext()) {
            TournamentItem t = iterator.next();
            Date end = t.getEndDate();
            if (end != null && end.before(now)) {
                pastTournaments.add(t);
                iterator.remove();

                changed = true;
            }
        }

        if (changed) {
            saveTournaments(context);
        }
    }

    public int getLikeCountForTournament(Context context, String tournamentId) {
        SharedPreferences prefs = context.getSharedPreferences("likes_prefs", Context.MODE_PRIVATE);
        int count = 0;

        for (String key : prefs.getAll().keySet()) {
            if (key.endsWith("_" + tournamentId)) {
                boolean liked = prefs.getBoolean(key, false);
                if (liked) {
                    count++;
                }
            }
        }
        return count;

    }

    public int getDislikeCountForTournament(Context context, String tournamentId) {
        SharedPreferences prefs = context.getSharedPreferences("likes_prefs", Context.MODE_PRIVATE);
        int count = 0;

        for (String key : prefs.getAll().keySet()) {
            if (key.endsWith("_" + tournamentId)) {
                boolean liked = prefs.getBoolean(key, true);
                if (!liked) {
                    count++;
                }
            }
        }
        return count;

    }
}









