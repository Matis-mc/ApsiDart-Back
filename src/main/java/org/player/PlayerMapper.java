package org.player;

import org.dart.dto.PlayerDto;
import org.player.entity.Player;

public class PlayerMapper {


    public static PlayerDto entityToDto(Player p){
        return new PlayerDto(String.valueOf(p.id), p.firstName, p.lastName, p.pseudo);
    }

}
