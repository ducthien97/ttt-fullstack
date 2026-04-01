package com.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "game_players")
public class GamePlayer {
    @Setter
    @Getter
    @Embeddable
    public static class GamePlayerId implements Serializable {
        @Column(name = "game_session_id")
        private UUID gameSessionId;

        @Column(name = "player_id")
        private UUID playerId;

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof GamePlayerId that)) return false;
            return Objects.equals(gameSessionId, that.gameSessionId) && Objects.equals(playerId, that.playerId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(gameSessionId, playerId);
        }
    }

    @EmbeddedId
    private GamePlayerId id;

    @Column(name = "symbol", nullable = false, length = 1)
    private char symbol;

}
