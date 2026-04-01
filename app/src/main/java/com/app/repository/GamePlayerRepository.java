package com.app.repository;

import com.app.model.GamePlayer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GamePlayerRepository extends JpaRepository<GamePlayer, GamePlayer.GamePlayerId> {
    List<GamePlayer> findByIdGameSessionId(UUID gameSessionId);

}
