package com.example.mercanteinfiera.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
public class Round {
    @Id @GeneratedValue
    private Long id;

    private Integer roundNumber;

    @Column(length = 2000)
    private String questionText;

    @Enumerated(EnumType.STRING)
    private RoundStatus status;

    @OneToMany(mappedBy = "round", cascade = CascadeType.ALL)
    private List<RoundItem> items = new ArrayList<>();

    public Long getId() { return id; }
    public Integer getRoundNumber() { return roundNumber; }
    public void setRoundNumber(Integer roundNumber) { this.roundNumber = roundNumber; }
    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }
    public RoundStatus getStatus() { return status; }
    public void setStatus(RoundStatus status) { this.status = status; }
    public List<RoundItem> getItems() { return items; }
}
