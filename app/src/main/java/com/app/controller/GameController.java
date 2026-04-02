package com.app.controller;

import com.app.dto.CreateOrJoinGameSessionRequestDTO;
import com.app.dto.GameSessionResponseDTO;
import com.app.dto.MakeMoveGameSessionRequestDTO;
import com.app.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/games")
public class GameController {
    private final GameService gameService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameSessionResponseDTO createGameSession(@RequestBody CreateOrJoinGameSessionRequestDTO gameSessionRequestDTO){
        return gameService.createGame(gameSessionRequestDTO.getPlayerName());
    }

    @PostMapping("/{code}/join")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public GameSessionResponseDTO joinGame(@RequestBody CreateOrJoinGameSessionRequestDTO gameSessionRequestDTO, @PathVariable("code") String code){
        return gameService.joinGame(gameSessionRequestDTO.getPlayerName(), code);
    }

    @GetMapping("/{code}")
    public GameSessionResponseDTO getGame(@PathVariable("code") String code){
        return gameService.getGame(code);
    }

    @PostMapping("/{code}/move")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public GameSessionResponseDTO makeMove(@RequestBody MakeMoveGameSessionRequestDTO makeMoveGameSessionRequestDTO, @PathVariable("code") String code){
        return gameService.makeMove(code, makeMoveGameSessionRequestDTO.getPlayerName(), makeMoveGameSessionRequestDTO.getMoveIndex());
    }
}
