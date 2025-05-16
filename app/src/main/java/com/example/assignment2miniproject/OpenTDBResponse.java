package com.example.assignment2miniproject;
import java.util.List;

public class OpenTDBResponse {
    private int response_code;
    private List<Question> results;

    public int getResponseCode() {
        return response_code;
    }

    public List<Question> getResults() {
        return results;
    }
}
