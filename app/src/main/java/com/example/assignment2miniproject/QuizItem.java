package com.example.assignment2miniproject;

import java.util.ArrayList;
import java.util.List;

public class QuizItem {
    private String id;
    private String category;
    private String difficulty;

    private String questionText;


    public QuizItem(String category, String difficulty, String questionText) {
        this.category = category;
        this.difficulty = difficulty;
        this.questionText = questionText;
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

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static List<QuizItem> fromQuestions(List<Question> questions) {
        List<QuizItem> items = new ArrayList<>();
        for (Question q : questions) {
            items.add(new QuizItem(q.getCategory(), q.getDifficulty(), q.getQuestionText()));
        }
        return items;

    }
}

