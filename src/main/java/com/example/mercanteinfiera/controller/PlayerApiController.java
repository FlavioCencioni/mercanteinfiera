package com.example.mercanteinfiera.controller;

import com.example.mercanteinfiera.dto.Dtos;
import com.example.mercanteinfiera.service.GameService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PlayerApiController {

    private final GameService gameService;

    public PlayerApiController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/rounds/current")
    public Dtos.CurrentRoundResponse currentRound() {
        return gameService.getCurrentRound();
    }

    @PostMapping("/players")
    public Dtos.PlayerResponse createOrGetPlayer(
            @RequestBody Dtos.CreatePlayerRequest req
    ) {
        return gameService.createOrGetPlayer(req);
    }

    @PostMapping("/submissions")
    public Dtos.SubmitAnswerResponse submit(
            @RequestBody Dtos.SubmitAnswerRequest req
    ) {
        return gameService.submitAnswer(req);
    }
}
