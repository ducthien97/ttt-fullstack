package com.app.controller;

import com.app.dto.CreatePlayerRequestDTO;
import com.app.dto.PlayerDTO;
import com.app.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/players")
public class PlayerController {
    private final PlayerService playerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlayerDTO createPlayer(@RequestBody CreatePlayerRequestDTO player) {
        return playerService.toDto(playerService.findOrCreatePlayer(player.getName()));
    }

}
