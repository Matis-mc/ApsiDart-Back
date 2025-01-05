package org.game.model;

import java.util.Map;

public record ClassementElement(
    Long idPlayer,
    double elo,
    Integer positionClassement,
    Double score,
    Map<String, Object> properties) {}
