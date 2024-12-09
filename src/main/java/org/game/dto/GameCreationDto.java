package org.game.dto;

import java.util.List;
import java.util.Map;

import org.game.model.TypeGame;
import org.player.entity.Player;


public record GameCreationDto(
    TypeGame typeJeu,
    List<Player> participants,
    Map<String, Object> properties
) {
    
}
