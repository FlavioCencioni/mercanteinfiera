package com.example.mercanteinfiera.model;

import jakarta.persistence.*;

@Entity
public class Player {
    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String nickname;

    public Long getId() { return id; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
}
