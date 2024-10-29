package org.game;

import org.game.dto.GameCreationDto;
import org.game.enums.CodeTypGameEnum;

import jakarta.activation.UnsupportedDataTypeException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class GameFactory {

    @Inject
    DartCricketGameControl dartCricketGameControl;

    @Inject
    DartX01GameControl dartX01GameControl;

    public Long initGame(GameCreationDto payload) throws UnsupportedDataTypeException{
        return switch (payload.typeJeu().code()){
            case "DART_CRICKET" -> dartCricketGameControl.initGame(payload);
            case "DART_301", "DART_501" -> dartX01GameControl.initGame(payload);
            default: -> throw new UnsupportedDataTypeException("Aucun mode de jeu ne correspond au code renseigné");
        }

    }
    
}
