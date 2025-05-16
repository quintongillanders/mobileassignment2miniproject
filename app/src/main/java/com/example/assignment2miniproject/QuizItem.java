package com.example.assignment2miniproject;

import java.util.ArrayList;
import java.util.List;

public class QuizItem {
    private String category;
    private String difficulty;
    private String sampleQuestion;

    public QuizItem(String category, String difficulty, String SampleQuestion) {
        this.category = category;
        this.difficulty = difficulty;
        this.sampleQuestion = sampleQuestion;
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

    public void setSampleQuestion(String sampleQuestion) {
        this.sampleQuestion = sampleQuestion;
    }

    public String getSampleQuestion() {
        return sampleQuestion;
    }

    public static List<QuizItem> fromQuestions(List<Question> questions) {
        List<QuizItem> items = new ArrayList<>();
        for (Question q : questions) {
            items.add(new QuizItem(q.getCategory(), q.getDifficulty(), q.getQuestionText()));
        }
        return items;

    }
}
