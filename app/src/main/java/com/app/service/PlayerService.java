package com.app.service;

import com.app.model.Player;
import com.app.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;

    public Player findOrCreatePlayer(String playerName){

        Player existingPlayer = findExistingPlayerByName(playerName);

        if (existingPlayer != null){
            return existingPlayer;
        }
        else {
            Player player = new Player();
            player.setName(playerName);
            return playerRepository.save(player);
        }
    }

    private Player findExistingPlayerByName (String name){
        return playerRepository.findByName(name).orElse(null);
    }

    public Player findExistingPlayer (UUID id) {
        return  playerRepository.findById(id).orElse(null);
    }

//    commented out for now but will be used to create players from the endpoint later
//    private PlayerDTO toDtoMapper(Player player){
//        PlayerDTO playerDTO = new PlayerDTO();
//        playerDTO.setId(player.getId());
//        playerDTO.setName(player.getName());
//        return playerDTO;
//    }


}
