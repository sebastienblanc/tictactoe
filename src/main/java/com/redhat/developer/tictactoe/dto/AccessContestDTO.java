package com.redhat.developer.tictactoe.dto;

import com.redhat.developer.tictactoe.model.Contest;
import com.redhat.developer.tictactoe.model.Player;

public class AccessContestDTO {
    
    private String user;
    private String userId;
    private String contestId;
    private String team;
    private long timeoutInSeconds = 60;

    public String getUser() {
        return user;
    }

    public String getContestId() {
        return contestId;
    }

    public String getUserId() {
        return userId;
    }

    public long getTimeoutInSeconds() {
        return timeoutInSeconds;
    }

    private AccessContestDTO(String userId, String user, String contestId, String team, long timeoutInSeconds) {
        this.user = user;
        this.userId = userId;
        this.contestId = contestId;
        this.team = team;
        this.timeoutInSeconds = timeoutInSeconds;
    }

    public static AccessContestDTO of(Player user, Contest contest) {
        String team = user.teamCross ? "CROSS" : "CIRCLE";
        return new AccessContestDTO(user.userId, user.username, contest.contestId, team, contest.timeBetweenRounds.toSeconds());
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

}