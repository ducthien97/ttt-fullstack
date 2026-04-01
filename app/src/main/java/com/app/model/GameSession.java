package com.app.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "game_sessions")
public class GameSession extends BaseEntity {
    @Column(name = "status", nullable = false)
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int[] getBoard() {
        return board;
    }

    public void setBoard(int[] board) {
        this.board = board;
    }

    public String getConnectCode() {
        return connectCode;
    }

    public void setConnectCode(String connectCode) {
        this.connectCode = connectCode;
    }

    public Player getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(Player currentTurn) {
        this.currentTurn = currentTurn;
    }

    public Player getWinnerId() {
        return winner;
    }

    public void setWinnerId(Player winner) {
        this.winner = winner;
    }
}

