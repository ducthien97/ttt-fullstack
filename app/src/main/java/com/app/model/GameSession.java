package com.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Setter
@Getter
@Entity
@Table(name = "game_sessions")
public class GameSession extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "board", nullable = false, columnDefinition = "jsonb")
    private int[] board;

    @Column(name = "connect_code", nullable = false, length = 5)
    private String connectCode;

    @ManyToOne
    @JoinColumn(name = "current_turn")
    private Player currentTurn;

    @ManyToOne
    @JoinColumn(name = "winner_id")
    private Player winner;

}

