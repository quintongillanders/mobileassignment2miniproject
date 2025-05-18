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

    public String getCategory() {
        return category;
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

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likes) {
        this.likeCount = likeCount;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(int dislikes) {

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


    public String getId() {
        return id;
    }

    public void setId(String id) {

        this.id = id;
    }
}
