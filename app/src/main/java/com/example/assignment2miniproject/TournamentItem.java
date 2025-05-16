package com.example.assignment2miniproject;

import java.util.Date;

public class TournamentItem {
    private String category;
    private String sampleQuestion;
    private String difficulty;

    private Date startDate;
    private Date endDate;

    public TournamentItem(String category, String sampleQuestion, Date startDate, Date endDate) {
        this.category = category;
        this.sampleQuestion = sampleQuestion;
        this.difficulty = difficulty;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
