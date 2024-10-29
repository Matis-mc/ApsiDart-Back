package org.game.dto;

import java.util.List;
import java.util.Map;

import org.game.model.TypeGame;
import org.player.entity.Player;

import jakarta.validation.constraints.NotNull;

public record GameCreationDto(
    @NotNull TypeGame typeJeu,
    List<Player> participants,
    Map<String, Object> properties
) {
    
}
