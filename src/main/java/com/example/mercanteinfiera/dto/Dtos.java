package com.example.mercanteinfiera.dto;

import java.time.Instant;
import java.util.List;

public final class Dtos {

    private Dtos() {}

    public record CreatePlayerRequest(String nickname) {}

    public record PlayerResponse(Long playerId, String nickname) {}

    public record RoundItemResponse(Long itemId, String label) {}

    public record CurrentRoundResponse(
            Long roundId,
            Integer roundNumber,
            String questionText,
            List<RoundItemResponse> items
    ) {}

    public record SubmitAnswerRequest(
            Long playerId,
            Long roundId,
            List<Long> orderedItemIds
    ) {}

    public record SubmitAnswerResponse(
            Long submissionId,
            Instant submittedAt
    ) {}

    public record AdminRoundSummary(
            Long roundId,
            Integer roundNumber,
            String status,
            long submissions
    ) {}

    public record AdminSubmissionResponse(
            Long submissionId,
            Long playerId,
            String nickname,
            Instant submittedAt,
            List<String> orderedLabels
    ) {}
}
