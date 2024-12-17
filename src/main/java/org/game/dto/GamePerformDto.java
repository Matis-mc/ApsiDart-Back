package org.game.dto;

import java.util.List;
import java.util.Map;

import org.game.model.TypeGame;

public record GamePerformDto(
    TypeGame modeJeu,
    Long idJeu,
    List<Object> performances,
    Map<String, Object> properties
) {
    
}
