package com.redhat.developer.tictactoe.dto;

import java.time.Duration;


public class ContestDTO {
    
    private String name;
    private Duration durationBetweenRounds;

    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Duration getDurationBetweenRounds() {
        return durationBetweenRounds;
    }

    public void setDurationBetweenRounds(Duration durationBetweenRounds) {
        this.durationBetweenRounds = durationBetweenRounds;
    }
}