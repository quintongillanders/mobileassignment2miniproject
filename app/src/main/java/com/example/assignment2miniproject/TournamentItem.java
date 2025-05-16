package com.example.assignment2miniproject;

public class TournamentItem {
    private String category;
    private String sampleQuestion;
    private String difficulty;

    public TournamentItem(String category, String sampleQuestion, String difficulty) {
        this.category = category;
        this.sampleQuestion = sampleQuestion;
        this.difficulty = difficulty;
    }

    public String getCategory() {
        return category;
    }

    public String getSampleQuestion() {
        return sampleQuestion;
    }

    public String getDifficulty() {
        return difficulty;
    }
}
