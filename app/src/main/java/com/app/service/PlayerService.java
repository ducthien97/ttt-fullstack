package com.app.service;

import com.app.dto.PlayerDTO;
import com.app.model.Player;
import com.app.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;

    public Player findOrCreatePlayer(String playerName) {
        return playerRepository.findByName(playerName)
                .orElseGet(() -> {
                    Player player = new Player();
                    player.setName(playerName);
                    return playerRepository.save(player);
                });
    }

    public Player findExistingPlayer(UUID id) {
        return playerRepository.findById(id).orElse(null);
    }

    public PlayerDTO toDto(Player player) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setId(player.getId());
        playerDTO.setName(player.getName());
        return playerDTO;
    }
}
