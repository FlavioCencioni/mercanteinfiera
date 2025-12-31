package com.example.mercanteinfiera.service;

import com.example.mercanteinfiera.api.ApiException;
import com.example.mercanteinfiera.dto.Dtos;
import com.example.mercanteinfiera.model.*;
import com.example.mercanteinfiera.repo.RoundRepo;
import com.example.mercanteinfiera.repo.SubmissionRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {

    private final RoundRepo roundRepo;
    private final SubmissionRepo submissionRepo;

    public AdminService(RoundRepo roundRepo, SubmissionRepo submissionRepo) {
        this.roundRepo = roundRepo;
        this.submissionRepo = submissionRepo;
    }

    @Transactional(readOnly = true)
    public List<Dtos.AdminRoundSummary> listRounds() {
        return roundRepo.findAll().stream()
                .map(r -> new Dtos.AdminRoundSummary(
                        r.getId(),
                        r.getRoundNumber(),
                        r.getStatus().name(),
                        submissionRepo.countByRoundId(r.getId())
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Dtos.AdminSubmissionResponse> getSubmissionsForRound(Long roundId) {
        roundRepo.findById(roundId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Round non trovato"));

        return submissionRepo.findByRoundIdOrderBySubmittedAtAsc(roundId).stream()
                .map(s -> new Dtos.AdminSubmissionResponse(
                        s.getId(),
                        s.getPlayer().getId(),
                        s.getPlayer().getNickname(),
                        s.getSubmittedAt(),
                        s.getItems().stream()
                                .sorted((a,b) -> a.getChosenIndex() - b.getChosenIndex())
                                .map(i -> i.getItem().getLabel())
                                .toList()
                ))
                .toList();
    }

    @Transactional
    public void openRound(Long roundId) {
        if (roundRepo.existsByStatus(RoundStatus.OPEN)) {
            throw new ApiException(HttpStatus.CONFLICT, "Esiste già un round aperto");
        }

        Round r = roundRepo.findById(roundId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Round non trovato"));
        r.setStatus(RoundStatus.OPEN);
        roundRepo.save(r);
    }

    @Transactional
    public void closeRound(Long roundId) {
        Round r = roundRepo.findById(roundId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Round non trovato"));
        r.setStatus(RoundStatus.CLOSED);
        roundRepo.save(r);
    }
}
