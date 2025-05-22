package com.example.assignment2miniproject;

import java.util.List;
public class QuizDataHolder {
    private static QuizDataHolder instance;
    private List<Question> questions;

    private QuizDataHolder() {}

    public static QuizDataHolder getInstance() {
        if (instance == null) {
            instance = new QuizDataHolder();
        }

    return instance;

}
public void setQuestions(List<Question> questions) {
    this.questions = questions;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
