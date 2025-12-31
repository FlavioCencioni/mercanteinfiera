package com.example.mercanteinfiera.repo;

import com.example.mercanteinfiera.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface RoundRepo extends JpaRepository<Round, Long> {
    Optional<Round> findFirstByStatus(RoundStatus status);
    boolean existsByStatus(RoundStatus status);
}
