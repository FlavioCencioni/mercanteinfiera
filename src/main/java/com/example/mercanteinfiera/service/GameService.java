package com.example.mercanteinfiera.service;

import com.example.mercanteinfiera.api.ApiException;
import com.example.mercanteinfiera.dto.Dtos;
import com.example.mercanteinfiera.model.*;
import com.example.mercanteinfiera.repo.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class GameService {

    private final RoundRepo roundRepo;
    private final RoundItemRepo roundItemRepo;
    private final PlayerRepo playerRepo;
    private final SubmissionRepo submissionRepo;

    public GameService(
            RoundRepo roundRepo,
            RoundItemRepo roundItemRepo,
            PlayerRepo playerRepo,
            SubmissionRepo submissionRepo
    ) {
        this.roundRepo = roundRepo;
        this.roundItemRepo = roundItemRepo;
        this.playerRepo = playerRepo;
        this.submissionRepo = submissionRepo;
    }

    @Transactional(readOnly = true)
    public Dtos.CurrentRoundResponse getCurrentRound() {
        Round round = roundRepo.findFirstByStatus(RoundStatus.OPEN)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Nessun round aperto"));

        List<Dtos.RoundItemResponse> items =
                roundItemRepo.findByRoundId(round.getId()).stream()
                        .map(i -> new Dtos.RoundItemResponse(i.getId(), i.getLabel()))
                        .toList();

        return new Dtos.CurrentRoundResponse(
                round.getId(),
                round.getRoundNumber(),
                round.getQuestionText(),
                items
        );
    }

    @Transactional
    public Dtos.PlayerResponse createOrGetPlayer(Dtos.CreatePlayerRequest req) {
        if (req.nickname() == null || req.nickname().isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "nickname obbligatorio");
        }

        Player player = playerRepo.findByNicknameIgnoreCase(req.nickname())
                .orElseGet(() -> {
                    Player p = new Player();
                    p.setNickname(req.nickname());
                    return playerRepo.save(p);
                });

        return new Dtos.PlayerResponse(player.getId(), player.getNickname());
    }

    @Transactional
    public Dtos.SubmitAnswerResponse submitAnswer(Dtos.SubmitAnswerRequest req) {
        Player player = playerRepo.findById(req.playerId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Player non trovato"));

        Round round = roundRepo.findById(req.roundId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Round non trovato"));

        if (round.getStatus() != RoundStatus.OPEN) {
            throw new ApiException(HttpStatus.CONFLICT, "Round non aperto");
        }

        if (submissionRepo.existsByRoundIdAndPlayerId(round.getId(), player.getId())) {
            throw new ApiException(HttpStatus.CONFLICT, "Risposta già inviata");
        }

        List<Long> ordered = req.orderedItemIds();
        if (ordered == null || ordered.size() != 10 || new HashSet<>(ordered).size() != 10) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Lista item non valida");
        }

        Map<Long, RoundItem> items = new HashMap<>();
        for (RoundItem it : roundItemRepo.findByRoundId(round.getId())) {
            items.put(it.getId(), it);
        }

        Submission submission = new Submission();
        submission.setPlayer(player);
        submission.setRound(round);

        for (int i = 0; i < ordered.size(); i++) {
            RoundItem item = items.get(ordered.get(i));
            if (item == null) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "Item non valido");
            }

            SubmissionItem si = new SubmissionItem();
            si.setSubmission(submission);
            si.setItem(item);
            si.setChosenIndex(i);
            submission.getItems().add(si);
        }

        submissionRepo.save(submission);
        return new Dtos.SubmitAnswerResponse(submission.getId(), submission.getSubmittedAt());
    }
}
