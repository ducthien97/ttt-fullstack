package com.app.repository;

import com.app.model.GameSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public interface GameSessionRepository extends JpaRepository<GameSession, UUID> {

    Optional<GameSession> findByConnectCode(String connectCode);

    @Query(value = "SELECT p.name, COUNT(g.winner_id) as wins\n" +
            "FROM players p\n" +
            "JOIN game_sessions g ON g.winner_id = p.id\n" +
            "GROUP BY p.id, p.name\n" +
            "ORDER BY wins DESC\n" +
            "LIMIT 5", nativeQuery = true)
    List<Object[]> findTopFivePlayers();



}
