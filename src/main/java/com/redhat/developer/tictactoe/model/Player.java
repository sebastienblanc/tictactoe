package com.redhat.developer.tictactoe.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Player extends PanacheEntity{

    @Column(unique = true) public String username;
    @Column public String userId;

    public Boolean teamCross;

    public static Player findPlayerByUserId(String userId) {
        return Player.find("userId", userId).singleResult();
    }

}