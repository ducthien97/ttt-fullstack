package com.app.controller;

import com.app.dto.LeaderboardEntry;
import com.app.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/leaderboard")
public class LeaderboardController {
    private final LeaderboardService leaderboardService;

    @GetMapping
    public List<LeaderboardEntry> getLeaderboardEntryList(){
        return leaderboardService.getLeaderBoard();
    }
}
