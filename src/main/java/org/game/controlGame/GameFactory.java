package org.game.controlGame;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.game.dto.GameCreationDto;
import org.game.dto.GamePerformDto;
import org.game.dto.GamePerformRetourDto;
import org.jboss.logging.Logger;

import io.quarkus.arc.All;
import jakarta.activation.UnsupportedDataTypeException;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class GameFactory {

    private static final Logger log = Logger.getLogger(GameFactory.class); 

    @Inject
    @All
    List<GameControl> gameControls;

    private Map<String, GameControl> controlMap = new HashMap<String, GameControl>();

    @PostConstruct
    void initGameMap(){
        gameControls.forEach(g -> {
            controlMap.put(g.getType(), g);
        });
    }


    public Long initGame(GameCreationDto payload) throws UnsupportedDataTypeException{
        return controlMap.get(payload.typeJeu().code().toString()).initGame(payload);
    }

    public GamePerformRetourDto performGame(GamePerformDto payload){
        return controlMap.get(payload.modeJeu().code().toString()).performOnGame(payload);
    }

    public void endGame(GamePerformDto payload){
        controlMap.get(payload.modeJeu().code().toString()).terminateGame(payload);
    }


}
