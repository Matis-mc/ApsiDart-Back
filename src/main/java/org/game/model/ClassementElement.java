package org.game.model;

import java.util.Map;

public record ClassementElement(
    Long idPlayer,
    Integer positionClassement,
    Double score,
    Map<String, Object> properties) {}
