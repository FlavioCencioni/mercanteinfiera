package com.example.mercanteinfiera.model;

import jakarta.persistence.*;

@Entity
public class RoundItem {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Round round;

    private String label;

    public Long getId() { return id; }
    public Round getRound() { return round; }
    public void setRound(Round round) { this.round = round; }
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
}
