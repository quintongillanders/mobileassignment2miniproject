package com.example.assignment2miniproject;

import java.util.Date;

public class TournamentItem {
    private String id;
    private String category;
    private String difficulty;

    private Date startDate;
    private Date endDate;

    private int likeCount;
    private int dislikeCount;


    public TournamentItem() {

    }

    public TournamentItem(String category, String difficulty, Date startDate, Date endDate) {
        this.category = category;
        this.difficulty = difficulty;
        this.startDate = startDate;
        this.endDate = endDate;
        this.likeCount = 0;
        this.dislikeCount = 0;
    }

    public TournamentItem(String id, String category, String difficulty, Date startDate, Date endDate) {
        this.id = id;
        this.category = category;
        this.difficulty = difficulty;
        this.startDate = startDate;
        this.endDate = endDate;
        this.likeCount = 0;
        this.dislikeCount = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory() {
        this.category = category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;

    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(int dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public void incrementLike() {
        this.likeCount++;
    }

    public void decrementLike() {
        if (this.likeCount > 0) this.likeCount--;
    }

    public void incrementDislike() {
        this.dislikeCount++;
    }

    public void decrementDislike() {
        if (this.dislikeCount > 0) this.dislikeCount--;
    }
}









