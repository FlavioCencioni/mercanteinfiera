package com.example.mercanteinfiera.repo;

import com.example.mercanteinfiera.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface SubmissionRepo extends JpaRepository<Submission, Long> {
    boolean existsByRoundIdAndPlayerId(Long roundId, Long playerId);
    List<Submission> findByRoundIdOrderBySubmittedAtAsc(Long roundId);
    long countByRoundId(Long roundId);
}
