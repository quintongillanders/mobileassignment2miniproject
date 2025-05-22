package com.example.assignment2miniproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Question {
    private String category;
    private String type;
    private String difficulty;
    private String question;
    private String correct_answer;
    private String[] incorrect_answers;

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String Text() {
        return question;
    }

    public String getCorrectAnswer() {
        return correct_answer;
    }

    public String getQuestionText() {
        return question;
    }

        public String[] getIncorrect_answers() {
            return incorrect_answers;
        }

        public List<String> getAllAnswersShuffled() {
    List<String> allAnswers= new ArrayList<>();

    if (incorrect_answers != null) {
        allAnswers.addAll(Arrays.asList(incorrect_answers));
    }

    if (correct_answer != null) {
        allAnswers.add(correct_answer);
    }
        Collections.shuffle(allAnswers);
            return allAnswers;
        }
}

