package com.example.mercanteinfiera.repo;

import com.example.mercanteinfiera.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface PlayerRepo extends JpaRepository<Player, Long> {
    Optional<Player> findByNicknameIgnoreCase(String nickname);
}
