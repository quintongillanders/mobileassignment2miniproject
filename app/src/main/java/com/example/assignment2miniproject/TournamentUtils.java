package com.example.assignment2miniproject;

import java.util.Date;

public class TournamentUtils {

    public static boolean isUpcoming(TournamentItem item) {
        return item.getEndDate().after(new Date());
    }

    public static boolean isPast(TournamentItem item) {
        return item.getEndDate().before(new Date());
    }
}
