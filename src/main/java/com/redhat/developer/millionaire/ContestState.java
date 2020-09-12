package com.redhat.developer.millionaire;

import java.time.Instant;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import com.redhat.developer.tictactoe.model.Contest;

@ApplicationScoped
public class ContestState {
    
    private Contest currentContest;
    private Instant roundTime;
  
    public void reset() {
        this.currentContest = null;
        this.roundTime = null;
    }

    public void setRoundTime() {
        this.roundTime = Instant.now();
    }

    public Instant getRoundTime() {
        return roundTime;
    }

    public Optional<Contest> getCurrentContest() {
        return Optional.ofNullable(this.currentContest);
    }

    public void startContest(Contest currentContest) {
        this.currentContest = currentContest;
    }



}