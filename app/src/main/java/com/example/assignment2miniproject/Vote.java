package com.example.assignment2miniproject;

import  android.content.Context;
import android.content.SharedPreferences;

public class Vote {
    private static final String PREF_NAME = "quiz_votes";
    private final SharedPreferences prefs;
    private final String keyPrefix;

    public Vote(Context context, String userId) {
        prefs = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        keyPrefix = userId + "_";
    }

    public boolean hasVoted(String quizId) {
    return prefs.contains(keyPrefix + quizId  + "_voted");
    }

    public boolean isLiked(String quizId) {
        return prefs.getBoolean(keyPrefix + quizId, true); // if false, dislike quiz, if true, liked quiz
        }

        public void saveVoteWithCount(String quizId,boolean liked, int likes, int dislikes) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(keyPrefix + quizId + "_voted", true);
        editor.putBoolean(keyPrefix + quizId + "_liked", liked);
        editor.putBoolean(keyPrefix + quizId, liked);
        editor.putInt(keyPrefix + quizId + "_likes", likes);
        editor.putInt(keyPrefix + quizId + "_dislikes", dislikes);
        editor.apply();
    }

    public int getLikes(String quizId, int defaultValue) {
        return prefs.getInt(keyPrefix + quizId + "_likes", defaultValue);
    }

    public int getDislikes(String quizId, int defaultValue) {
        return prefs.getInt(keyPrefix + quizId + "_dislikes", defaultValue);
    }
}
