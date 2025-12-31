package com.example.mercanteinfiera.controller;

import com.example.mercanteinfiera.dto.Dtos;
import com.example.mercanteinfiera.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminApiController {

    private final AdminService adminService;

    public AdminApiController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/rounds")
    public List<Dtos.AdminRoundSummary> rounds() {
        return adminService.listRounds();
    }

    @GetMapping("/rounds/{roundId}/submissions")
    public List<Dtos.AdminSubmissionResponse> submissions(
            @PathVariable Long roundId
    ) {
        return adminService.getSubmissionsForRound(roundId);
    }

    @PostMapping("/rounds/{roundId}/open")
    public void open(@PathVariable Long roundId) {
        adminService.openRound(roundId);
    }

    @PostMapping("/rounds/{roundId}/close")
    public void close(@PathVariable Long roundId) {
        adminService.closeRound(roundId);
    }
}
