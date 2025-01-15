package org.game.model;

import java.util.Map;

public record ClassementElement(
    Long idPlayer,
    Double elo,
    Integer positionClassement,
    Double score,
    Map<String, Object> properties) {}
