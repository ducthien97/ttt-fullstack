package com.app.service;

import com.app.dto.LeaderboardEntry;
import com.app.repository.GameSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaderboardService {
    private final GameSessionRepository gameSessionRepository;

    public List<LeaderboardEntry> getLeaderBoard() {
        List<LeaderboardEntry> leaderboardEntries = new ArrayList<>();
        List<Object[]> topFiveRawResult = gameSessionRepository.findTopFivePlayers();

        for (Object[] resultRow : topFiveRawResult){
            LeaderboardEntry entry = new LeaderboardEntry();
            entry.setPlayerName(resultRow[0].toString());
            entry.setWinCount(((java.math.BigInteger) resultRow[1]).intValue());

            leaderboardEntries.add(entry);
        }

        return leaderboardEntries;
    }
}
