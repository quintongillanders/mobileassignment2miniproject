package com.example.assignment2miniproject;

import java.util.ArrayList;
import java.util.List;

public class QuizItem {
    private String category;
    private String difficulty;


    public QuizItem(String category, String difficulty, String questionText) {
        this.category = category;
        this.difficulty = difficulty;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty() {
        this.difficulty = difficulty;
    }

    public static List<QuizItem> fromQuestions(List<Question> questions) {
        List<QuizItem> items = new ArrayList<>();
        for (Question q : questions) {
            items.add(new QuizItem(q.getCategory(), q.getDifficulty(), q.getQuestionText()));
        }
        return items;

    }
}
