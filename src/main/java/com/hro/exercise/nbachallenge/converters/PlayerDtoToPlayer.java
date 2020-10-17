package com.hro.exercise.nbachallenge.converters;

import com.hro.exercise.nbachallenge.command.PlayerDto;
import com.hro.exercise.nbachallenge.persistence.model.Player;
import com.hro.exercise.nbachallenge.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayerDtoToPlayer extends AbstractConverter<PlayerDto, Player>{

    private PlayerService playerService;

    @Autowired
    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public Player convert(PlayerDto playerDto) {

        Player player = (playerDto.getId() != null ? playerService.get(playerDto.getId()) : new Player());
        player.setLastName(playerDto.getLastName());
        player.setFirstName(playerDto.getFirstName());
        System.out.println(player);

        return player;
    }
}
