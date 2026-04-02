package com.app.service;

import com.app.dto.LeaderboardEntry;
import com.app.repository.GameSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaderboardService {
    private final GameSessionRepository gameSessionRepository;

    public List<LeaderboardEntry> getLeaderBoard() {
        return gameSessionRepository.findTopFivePlayers().stream()
                .map(row -> {
                    LeaderboardEntry entry = new LeaderboardEntry();
                    entry.setPlayerName(row[0].toString());
                    entry.setWinCount(((Long) row[1]).intValue());
                    return entry;
                })
                .toList();
    }
}
