package com.app.dto;

import java.util.UUID;

public class GameSessionResponseDTO {
    private UUID id;
    private String status;
    private int[] board;
    private String currentTurnPlayerName;
    private String winnerName;
    private String connectCode;
    private Character playerSymbol;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public String getCurrentTurnPlayerName() {
        return currentTurnPlayerName;
    }

    public void setCurrentTurnPlayerName(String currentTurnPlayerName) {
        this.currentTurnPlayerName = currentTurnPlayerName;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    public String getConnectCode() {
        return connectCode;
    }

    public void setConnectCode(String connectCode) {
        this.connectCode = connectCode;
    }

    public Character getPlayerSymbol() {
        return playerSymbol;
    }

    public void setPlayerSymbol(Character playerSymbol) {
        this.playerSymbol = playerSymbol;
    }
}
