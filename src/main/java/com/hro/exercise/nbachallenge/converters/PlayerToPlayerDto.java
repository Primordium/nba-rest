package com.hro.exercise.nbachallenge.converters;

import com.hro.exercise.nbachallenge.command.PlayerDto;
import com.hro.exercise.nbachallenge.persistence.model.Player;
import org.springframework.stereotype.Component;

@Component
public class PlayerToPlayerDto extends AbstractConverter<Player, PlayerDto>{

    @Override
    public PlayerDto convert(Player player) {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setFirstName(player.getFirstName());
        playerDto.setLastName(player.getLastName());
        playerDto.setId(player.getId());

        return playerDto;
    }
}
