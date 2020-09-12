package com.redhat.developer.millionaire;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import io.quarkus.arc.Lock;

import javax.enterprise.context.ApplicationScoped;

import com.redhat.developer.tictactoe.model.Contest;

// This class should use Redis to store statistics.
// Needs to lock methods (waiting for Quarkus 1.7.0 for better experience)

@ApplicationScoped
@Lock
public class Statistics {
    
    private long numberOfCrossUsers = 0;
    private long numberOfCircleUsers = 0;

    
    public void reset() {
        this.numberOfCrossUsers = 0;
        this.numberOfCircleUsers = 0;
    }

   
    public void update(Contest contest) {
        numberOfCircleUsers = contest.teamCircle.players.size();
        numberOfCrossUsers = contest.teamCross.players.size();
    }

  
    public long getNumberOfCrossUsers() {
        return numberOfCrossUsers;
    }

    public long getNumberOfCircleUsers() {
        return numberOfCircleUsers;
    }



       

}