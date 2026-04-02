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

import static com.app.game.GameLogic.*;

@Service
@RequiredArgsConstructor
public class GameService {
    private final PlayerService playerService;
    private final GameSessionRepository gameSessionRepository;
    private final GamePlayerRepository gamePlayerRepository;

    private static final Character X = 'X';
    private static final Character O = 'O';




    public GameSessionResponseDTO createGame(String playerName){
        Player playerFound = playerService.findOrCreatePlayer(playerName);

        int[] emptyBoard = {0,0,0,0,0,0,0,0,0};
        String connectCode = generateConnectCode();

        while (gameSessionRepository.findByConnectCode(connectCode).isPresent()){
            connectCode = generateConnectCode();
        }

        GameSession newGameSession = new GameSession();
        newGameSession.setStatus(Status.WAITING);
        newGameSession.setBoard(emptyBoard);
        newGameSession.setConnectCode(connectCode);
        newGameSession.setCurrentTurn(playerFound);
        GameSession savedGameSession = gameSessionRepository.save(newGameSession);

        GamePlayer newGamePlayer = new GamePlayer();
        GamePlayer.GamePlayerId newGamePlayerId = new GamePlayer.GamePlayerId();
        newGamePlayerId.setGameSessionId(savedGameSession.getId());
        newGamePlayerId.setPlayerId(playerFound.getId());
        newGamePlayer.setId(newGamePlayerId);
        newGamePlayer.setSymbol(X);
        gamePlayerRepository.save(newGamePlayer);

        return toDto(savedGameSession, X);
    }

    public GameSessionResponseDTO joinGame(String playerName, String gameAccessCode){
        Player playerFound = playerService.findOrCreatePlayer(playerName);
        GameSession gameSessionFound = gameSessionRepository.findByConnectCode(gameAccessCode).orElseThrow(() -> new RuntimeException("Game session does not exist"));
        if (gameSessionFound.getCurrentTurn().getId().equals(playerFound.getId())) {
            throw new RuntimeException("You cannot join your own game");
        } else if  (gameSessionFound.getStatus().equals(Status.ACTIVE)) {
            throw new RuntimeException("Only two players can join one game");
        }  else if (gameSessionFound.getStatus().equals(Status.COMPLETE)){
            throw new RuntimeException("This game has expired. You can no longer join");
        }

        gameSessionFound.setStatus(Status.ACTIVE);
        GameSession savedGameSession = gameSessionRepository.save(gameSessionFound);


        GamePlayer newGamePlayer = new GamePlayer();
        GamePlayer.GamePlayerId newGamePlayerId = new GamePlayer.GamePlayerId();
        newGamePlayerId.setGameSessionId(gameSessionFound.getId());
        newGamePlayerId.setPlayerId(playerFound.getId());
        newGamePlayer.setId(newGamePlayerId);
        newGamePlayer.setSymbol(O);
        gamePlayerRepository.save(newGamePlayer);

        return toDto(savedGameSession, O);
    }

    public GameSessionResponseDTO getGame (String accessCode) {
        GameSession currentSession = gameSessionRepository
                .findByConnectCode(accessCode)
                .orElseThrow(() -> new RuntimeException("Game session does not exist"));

        return toDto(currentSession, null);
    }

    public GameSessionResponseDTO makeMove (String accessCode, String playerName, int moveIndex){
        GameSession foundGameSession = gameSessionRepository.findByConnectCode(accessCode).orElseThrow(() -> new RuntimeException("Game session does not exist"));
        Player foundPlayer = playerService.findOrCreatePlayer(playerName);

        if (!foundGameSession.getStatus().equals(Status.ACTIVE)){
            throw new RuntimeException("This game is not valid");
        }

        int[] board = foundGameSession.getBoard();
        if (!isValidMove(board, moveIndex)){
            throw new RuntimeException("Invalid move");
        }

        if (!foundGameSession.getCurrentTurn().getId().equals(foundPlayer.getId())){
            throw new RuntimeException("It's not your turn");
        }

        List<GamePlayer> gamePlayers = gamePlayerRepository.findByIdGameSessionId(foundGameSession.getId());
        GamePlayer currentGamePlayer;
        GamePlayer otherGamePlayer;

        if (gamePlayers.getFirst().getId().getPlayerId().equals(foundPlayer.getId())){
            currentGamePlayer = gamePlayers.getFirst();
            otherGamePlayer = gamePlayers.getLast();
        } else {
            otherGamePlayer = gamePlayers.getFirst();
            currentGamePlayer = gamePlayers.getLast();
        }
        char currentGamePlayerSymbol = currentGamePlayer.getSymbol();
        board[moveIndex] = currentGamePlayerSymbol == X ? 1 : 2;
        foundGameSession.setBoard(board);
        if (checkWinner(board, currentGamePlayerSymbol == X ? 1 : 2)){
            foundGameSession.setStatus(Status.COMPLETE);
            foundGameSession.setWinner(foundPlayer);
        }
        else if (checkDraw(board)){
            foundGameSession.setStatus(Status.COMPLETE);
        }
        else {
            foundGameSession.setCurrentTurn(playerService.findExistingPlayer(otherGamePlayer.getId().getPlayerId()));
        }
        return toDto(gameSessionRepository.save(foundGameSession), null);
    }

    private String generateConnectCode(){
        int randomNumber = new Random().nextInt(100000);
        return String.format("%05d", randomNumber);
    }

    private GameSessionResponseDTO toDto (GameSession gameSession, Character playerSymbol){
        GameSessionResponseDTO dto = new GameSessionResponseDTO();
        dto.setId(gameSession.getId());
        dto.setBoard(gameSession.getBoard());
        dto.setStatus(gameSession.getStatus().name());
        dto.setConnectCode(gameSession.getConnectCode());
        dto.setPlayerSymbol(playerSymbol);
        if (gameSession.getCurrentTurn() != null){
            dto.setCurrentTurnPlayerName(gameSession.getCurrentTurn().getName());
        }
        if (gameSession.getWinner() != null){
            dto.setWinnerName(gameSession.getWinner().getName());
        }
        return dto;
    }
}
