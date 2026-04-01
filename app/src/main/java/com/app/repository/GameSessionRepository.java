package com.app.repository;

import com.app.model.GameSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GameSessionRepository extends JpaRepository<GameSession, UUID> {

    Optional<GameSession> findByConnectCode(String connectCode);
}
