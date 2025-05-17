package com.example.assignment2miniproject;

import java.util.Date;

public class TournamentItem {
    private String id;
    private String category;
    private String sampleQuestion;
    private String difficulty;

    private Date startDate;
    private Date endDate;

    private int likes = 0;
    private int dislikes = 0;

    private boolean hasVoted = false;

    public TournamentItem() {

    }

    public TournamentItem(String category, String difficulty, Date startDate, Date endDate) {
        this.category = category;
        this.difficulty = difficulty;
        this.startDate = startDate;
        this.endDate = endDate;
        this.likes = 0;
        this.dislikes = 0;


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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public boolean hasVoted() {
        return hasVoted;
    }

    public void setHasVoted(boolean hasVoted) {
        this.hasVoted = hasVoted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
