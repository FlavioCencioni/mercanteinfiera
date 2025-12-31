// FILE: src/main/java/com/example/mercanteinfiera/model/SubmissionItem.java
package com.example.mercanteinfiera.model;

import jakarta.persistence.*;

@Entity
public class SubmissionItem {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Submission submission;

    @ManyToOne
    private RoundItem item;

    private Integer chosenIndex;

    public Long getId() {
        return id;
    }

    public Submission getSubmission() {
        return submission;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }

    public RoundItem getItem() {
        return item;
    }

    public void setItem(RoundItem item) {
        this.item = item;
    }

    public Integer getChosenIndex() {
        return chosenIndex;
    }

    public void setChosenIndex(Integer chosenIndex) {
        this.chosenIndex = chosenIndex;
    }
}
