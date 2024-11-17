package org.game;

import java.util.HashMap;
import java.util.Map;

import org.common.exceptions.UnsupportedEntityTypeException;
import org.game.entity.DGame;
import org.game.entity.DPerform;
import org.game.enums.CodeTypGameEnum;
import org.game.model.ClassementElement;
import org.game.model.Game;
import org.game.model.TypeGame;

public class DartMapper {

    public static Game mapEntityToModel(DGame entity) {
            Game game = new Game();
            game.id = entity.id;
            game.statut = entity.statut;
            game.setModeJeu(new TypeGame(CodeTypGameEnum.DACKT, entity.type, "", null))
            game.setClassement(entity.dPerform.stream().map(DartMapper::mapContextToClassement).toList());
            return game;
    }


    public static ClassementElement mapContextToClassement(DPerform perform){
        Map<String, Object> properties = new HashMap<>();
        properties.put("volees", perform.volees);
        return new ClassementElement(
            perform.dartPlayer.id,
            perform.position, 
            Double.valueOf(perform.score.toString()), 
            properties);
    }

}