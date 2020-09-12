package com.redhat.developer.tictactoe.model;

import java.time.Duration;
import java.util.Date;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Table(indexes = {
    @Index(name = "contest_id_index", columnList = "contestId")
})
public class Contest extends PanacheEntity {
    
    @Column(name = "contestId", nullable = false, unique = true)
    public String contestId;

    @Column(nullable = false)
    public String name;

    @Column
    public Date creationTime;

    @Column
    public Duration timeBetweenRounds = Duration.ofMinutes(1);

    @OneToOne
    public Team teamCircle;
    
    @OneToOne
    public Team teamCross;

    @OneToOne
    public Board board;

    public static Optional<Contest> findByContestId(String contestId) {
        return Contest.find("contestId", contestId).singleResultOptional();
    }

    public void loadBalance(Player player) {
        int teamCrossPlayers = this.teamCross.players.size();
        int teamCirclePlayers = this.teamCircle.players.size();
        if(teamCirclePlayers==teamCrossPlayers){
            if(Math.random() > 0.5) {
                this.teamCircle.players.add(player);
                player.teamCross = false;
            }
            else {
                this.teamCross.players.add(player);
                player.teamCross = true;
            }
        }
        else {
            if(teamCirclePlayers>teamCrossPlayers){
                this.teamCross.players.add(player);
                player.teamCross = true;
            }
            else {
                this.teamCircle.players.add(player);
                player.teamCross = false;
            }
        }
    }

    public void initContest(){
        this.teamCircle = new Team();
        this.teamCross = new Team();
        this.board = new Board();
        this.board.persist();
        this.teamCircle.persist();
        this.teamCross.persist();
    }

}