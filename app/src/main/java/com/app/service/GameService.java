package com.app.service;

import com.app.dto.GameSessionResponseDTO;
import com.app.model.GamePlayer;
import com.app.model.GameSession;
import com.app.model.Player;
import com.app.model.Status;
import com.app.repository.GamePlayerRepository;
import com.app.repository.GameSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static com.app.game.GameLogic.*;

@Service
@RequiredArgsConstructor
public class GameService {
    private final PlayerService playerService;
    private final GameSessionRepository gameSessionRepository;
    private final GamePlayerRepository gamePlayerRepository;

    private static final char X = 'X';
    private static final char O = 'O';
    private static final Random RANDOM = new Random();

    public GameSessionResponseDTO createGame(String playerName) {
        Player player = playerService.findOrCreatePlayer(playerName);

        GameSession gameSession = new GameSession();
        gameSession.setStatus(Status.WAITING);
        gameSession.setBoard(new int[9]);
        gameSession.setConnectCode(generateUniqueConnectCode());
        gameSession.setCurrentTurn(player);
        GameSession saved = gameSessionRepository.save(gameSession);

        createGamePlayer(saved.getId(), player.getId(), X);

        return toDto(saved, X);
    }

    public GameSessionResponseDTO joinGame(String playerName, String gameAccessCode) {
        Player player = playerService.findOrCreatePlayer(playerName);
        GameSession gameSession = findSessionByCode(gameAccessCode);

        if (gameSession.getCurrentTurn().getId().equals(player.getId())) {
            throw new RuntimeException("You cannot join your own game");
        } else if (gameSession.getStatus().equals(Status.ACTIVE)) {
            throw new RuntimeException("Only two players can join one game");
        } else if (gameSession.getStatus().equals(Status.COMPLETE)) {
            throw new RuntimeException("This game has expired. You can no longer join");
        }

        gameSession.setStatus(Status.ACTIVE);
        GameSession saved = gameSessionRepository.save(gameSession);
        createGamePlayer(saved.getId(), player.getId(), O);

        return toDto(saved, O);
    }

    public GameSessionResponseDTO getGame(String accessCode) {
        return toDto(findSessionByCode(accessCode), null);
    }

    public GameSessionResponseDTO makeMove(String accessCode, String playerName, int moveIndex) {
        GameSession gameSession = findSessionByCode(accessCode);
        Player player = playerService.findOrCreatePlayer(playerName);

        if (!gameSession.getStatus().equals(Status.ACTIVE)) {
            throw new RuntimeException("This game is not valid");
        }
        if (!isValidMove(gameSession.getBoard(), moveIndex)) {
            throw new RuntimeException("Invalid move");
        }
        if (!gameSession.getCurrentTurn().getId().equals(player.getId())) {
            throw new RuntimeException("It's not your turn");
        }

        List<GamePlayer> gamePlayers = gamePlayerRepository.findByIdGameSessionId(gameSession.getId());
        GamePlayer currentGamePlayer = gamePlayers.getFirst().getId().getPlayerId().equals(player.getId())
                ? gamePlayers.getFirst()
                : gamePlayers.getLast();
        GamePlayer otherGamePlayer = currentGamePlayer == gamePlayers.getFirst()
                ? gamePlayers.getLast()
                : gamePlayers.getFirst();

        int[] board = gameSession.getBoard();
        int playerNum = currentGamePlayer.getSymbol() == X ? 1 : 2;
        board[moveIndex] = playerNum;
        gameSession.setBoard(board);

        if (checkWinner(board, playerNum)) {
            gameSession.setStatus(Status.COMPLETE);
            gameSession.setWinner(player);
        } else if (checkDraw(board)) {
            gameSession.setStatus(Status.COMPLETE);
        } else {
            gameSession.setCurrentTurn(playerService.findExistingPlayer(otherGamePlayer.getId().getPlayerId()));
        }

        return toDto(gameSessionRepository.save(gameSession), null);
    }

    private void createGamePlayer(UUID gameSessionId, UUID playerId, char symbol) {
        GamePlayer.GamePlayerId gamePlayerId = new GamePlayer.GamePlayerId();
        gamePlayerId.setGameSessionId(gameSessionId);
        gamePlayerId.setPlayerId(playerId);

        GamePlayer gamePlayer = new GamePlayer();
        gamePlayer.setId(gamePlayerId);
        gamePlayer.setSymbol(symbol);
        gamePlayerRepository.save(gamePlayer);
    }

    private GameSession findSessionByCode(String code) {
        return gameSessionRepository.findByConnectCode(code)
                .orElseThrow(() -> new RuntimeException("Game session does not exist"));
    }

    private String generateUniqueConnectCode() {
        String code;
        do {
            code = String.format("%05d", RANDOM.nextInt(100000));
        } while (gameSessionRepository.findByConnectCode(code).isPresent());
        return code;
    }

    private GameSessionResponseDTO toDto(GameSession gameSession, Character playerSymbol) {
        GameSessionResponseDTO dto = new GameSessionResponseDTO();
        dto.setId(gameSession.getId());
        dto.setBoard(gameSession.getBoard());
        dto.setStatus(gameSession.getStatus().name());
        dto.setConnectCode(gameSession.getConnectCode());
        dto.setPlayerSymbol(playerSymbol);
        if (gameSession.getCurrentTurn() != null) {
            dto.setCurrentTurnPlayerName(gameSession.getCurrentTurn().getName());
        }
        if (gameSession.getWinner() != null) {
            dto.setWinnerName(gameSession.getWinner().getName());
        }
        return dto;
    }
}
