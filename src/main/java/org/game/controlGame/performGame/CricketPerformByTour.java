package org.game.controlGame.performGame;

import java.util.Map;

import static org.common.DartConstant.Context.ID_JOUEUR;
import static org.common.DartConstant.Context.NUM_TOUR;
import static org.common.DartConstant.Context.PSEUDO;
import static org.common.DartConstant.Context.SCORE;
import org.game.dto.GamePerformDto;
import org.game.dto.dart.DartContextPropertyDto;

public class CricketPerformByTour implements CricketPerformGame{

    public static final String TOUR = "TOUR";

    @Override
    public String getMode() {
        return TOUR;
    }

    @Override
    public void persistPerformGame(GamePerformDto dto) {
        DartContextPropertyDto dcp = mapToContext(dto.properties());
        // todo : enregistrer stat, contacter ia ....


    }

    private void persistDPerformFromContext(String idJeu, DartContextPropertyDto context){
        //DPerform dp = DPerform.findById();
        }

    private DartContextPropertyDto mapToContext(Map<String, Object> properties){
        return new DartContextPropertyDto(
            extractStringProperty(ID_JOUEUR, properties),
            extractStringProperty(PSEUDO, properties),
            extractStringProperty(SCORE, properties),
            extractStringProperty(NUM_TOUR, properties),
            null,
            null);
    }

    // todo : definir une exception spéciale
    private String extractStringProperty(String key, Map<String, Object> properties){
        return (String) properties.get(key);
    }

    
    
}
