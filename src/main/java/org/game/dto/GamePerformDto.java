package org.game.dto;

import java.util.Map;

import org.game.model.TypeGame;

public record GamePerformDto(
    TypeGame modeJeu,
    Long idJeu,
    Map<String, Object> properties
) {
    
}
