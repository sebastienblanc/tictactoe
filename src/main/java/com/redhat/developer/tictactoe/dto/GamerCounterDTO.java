package com.redhat.developer.tictactoe.dto;

public class GamerCounterDTO implements ServerSideEventMessage {
    
    private long numberOfCrossPlayers;

    private long numberOfCirclePlayers;

    public GamerCounterDTO(long numberOfCrossPlayers, long numberOfCirclePlayers) {
        this.numberOfCrossPlayers = numberOfCrossPlayers;
        this.numberOfCirclePlayers = numberOfCirclePlayers;
    }
    
    public long getNumberOfCrossPlayers() {
        return numberOfCrossPlayers;
    }

    public long getNumberOfCirclePlayers() {
        return numberOfCirclePlayers;
    }
    
}