package com.example.mercanteinfiera.config;

import com.example.mercanteinfiera.model.Round;
import com.example.mercanteinfiera.model.RoundItem;
import com.example.mercanteinfiera.model.RoundStatus;
import com.example.mercanteinfiera.repo.RoundRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedRoundsFromFile(RoundRepo roundRepo, ObjectMapper objectMapper) {
        return args -> {
            if (roundRepo.count() > 0) return;

            ClassPathResource resource = new ClassPathResource("rounds.json");
            try (InputStream in = resource.getInputStream()) {
                RoundsFile file = objectMapper.readValue(in, RoundsFile.class);

                if (file.rounds == null || file.rounds.isEmpty()) return;

                int roundNumber = 1;

                for (RoundEntry entry : file.rounds) {
                    if (entry == null) continue;
                    if (entry.questionText == null || entry.questionText.isBlank()) continue;
                    if (entry.items == null || entry.items.size() != 10) continue;

                    Round r = new Round();
                    r.setRoundNumber(roundNumber++);
                    r.setQuestionText(entry.questionText.trim());
                    r.setStatus(r.getRoundNumber() == 1 ? RoundStatus.OPEN : RoundStatus.CLOSED);

                    for (String label : entry.items) {
                        if (label == null) continue;
                        RoundItem it = new RoundItem();
                        it.setRound(r);
                        it.setLabel(label.trim());
                        r.getItems().add(it);
                    }

                    // salva round + items (cascade)
                    roundRepo.save(r);
                }
            }
        };
    }

    // DTO per parsing JSON
    public static class RoundsFile {
        public List<RoundEntry> rounds;
    }

    public static class RoundEntry {
        public String questionText;
        public List<String> items;
    }
}
