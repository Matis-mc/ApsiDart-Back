package org.game;

import java.util.List;
import java.util.Map;

import org.game.dto.GameCreationDto;
import org.game.enums.CodeTypGameEnum;

import io.quarkus.arc.All;
import jakarta.activation.UnsupportedDataTypeException;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class GameFactory {

    @Inject
    @All
    List<GameControl> gameControls;

    private Map<String, GameControl> controlMap;

    @PostConstruct
    void initGameMap(){
        gameControls.forEach(g -> {
            controlMap.put(g.getType(), g);
        });
    }


    public Long initGame(GameCreationDto payload) throws UnsupportedDataTypeException{
        return controlMap.get(payload.typeJeu().code().toString()).initGame(payload);

    }
    
}
