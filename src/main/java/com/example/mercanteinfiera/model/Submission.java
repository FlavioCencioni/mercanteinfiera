package com.example.mercanteinfiera.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"round_id","player_id"}))
public class Submission {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Round round;

    @ManyToOne
    private Player player;

    private Instant submittedAt = Instant.now();

    @OneToMany(mappedBy = "submission", cascade = CascadeType.ALL)
    private List<SubmissionItem> items = new ArrayList<>();

    public Long getId() { return id; }
    public Round getRound() { return round; }
    public void setRound(Round round) { this.round = round; }
    public Player getPlayer() { return player; }
    public void setPlayer(Player player) { this.player = player; }
    public Instant getSubmittedAt() { return submittedAt; }
    public List<SubmissionItem> getItems() { return items; }
}
