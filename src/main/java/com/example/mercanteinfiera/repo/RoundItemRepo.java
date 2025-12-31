package com.example.mercanteinfiera.repo;

import com.example.mercanteinfiera.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface RoundItemRepo extends JpaRepository<RoundItem, Long> {
    List<RoundItem> findByRoundId(Long roundId);
}
