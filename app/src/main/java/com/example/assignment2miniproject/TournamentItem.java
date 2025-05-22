package com.example.assignment2miniproject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    public Integer getCategoryId() {
        if (category == null) return null;

        Map<String, Integer> categoryMap = new HashMap<>();
        // all of these quiz IDs come from https://OpenTDB.com
        categoryMap.put("General Knowledge", 9);
        categoryMap.put("Entertainment: Books", 10);
        categoryMap.put("Entertainment: Film", 11);
        categoryMap.put("Entertainment: Music", 12);
        categoryMap.put("Entertainment: Musicals & Theatres", 13);
        categoryMap.put("Entertainment: Television", 14);
        categoryMap.put("Entertainment: Video Games", 15);
        categoryMap.put("Entertainment: Board Games", 16);
        categoryMap.put("Science & Nature", 17);
        categoryMap.put("Science: Computers", 18);
        categoryMap.put("Science: Mathematics", 19);
        categoryMap.put("Mythology", 20);
        categoryMap.put("Sports", 21);
        categoryMap.put("Geography", 22);
        categoryMap.put("History", 23);
        categoryMap.put("Politics", 24);
        categoryMap.put("Art", 25);
        categoryMap.put("Celebrities", 26);
        categoryMap.put("Animals", 27);
        categoryMap.put("Vehicles", 28);
        categoryMap.put("Entertainment: Comics", 29);
        categoryMap.put("Science: Gadgets", 30);
        categoryMap.put("Entertainment: Japanese Anime & Manga", 31);
        categoryMap.put("Entertainment: Cartoon & Animations", 32);

        return categoryMap.getOrDefault(category, null);
    }
}









