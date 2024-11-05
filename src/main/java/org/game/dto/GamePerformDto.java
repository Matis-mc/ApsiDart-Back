package org.game.dto;

import java.util.Map;

import org.game.model.TypeGame;

import jakarta.validation.constraints.NotNull;

public record GamePerformDto(
    @NotNull TypeGame modeJeu,
    Long idJeu,
    Map<String, Object> properties
) {
    
}
