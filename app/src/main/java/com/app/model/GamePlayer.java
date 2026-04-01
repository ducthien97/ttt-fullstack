package com.app.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "game_players")
public class GamePlayer {
    @Embeddable
    static class GamePlayerId implements Serializable {
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

    public void setId(GamePlayerId id) {
        this.id = id;
    }

    public GamePlayerId getId() {
        return id;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }
}
