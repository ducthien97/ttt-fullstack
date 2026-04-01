package com.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "game_sessions")
public class GameSession extends BaseEntity {
    @Column(name = "status", nullable = false)
    private Status status;

    @Getter
    @Setter
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "board", nullable = false, columnDefinition = "jsonb")
    private int[] board;

    @Setter
    @Getter
    @Column(name = "connect_code", nullable = false, length = 5)
    private String connectCode;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "current_turn")
    private Player currentTurn;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "winner_id")
    private Player winner;

    public String getStatus() {
        return status.name();
    }

    public void setStatus(String status) {
        this.status = Status.valueOf(status);
    }

}

