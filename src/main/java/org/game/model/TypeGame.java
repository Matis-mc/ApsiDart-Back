package org.game.model;

import java.util.Map;

import org.game.enums.CodeTypGameEnum;

public record TypeGame (
    CodeTypGameEnum code, 
    String nom,
    String variante,
    Map<String, Object> properties){

}
