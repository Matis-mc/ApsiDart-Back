package org.game.controlGame.performGame;

import java.util.List;
import java.util.Map;

import static org.common.DartConstant.Context.DELTA;
import static org.common.DartConstant.Context.ID_JOUEUR;
import static org.common.DartConstant.Context.NUM_TOUR;
import static org.common.DartConstant.Context.PSEUDO;
import static org.common.DartConstant.Context.SCORE;
import static org.common.DartConstant.Context.VOLEE;
import org.game.dto.GamePerformDto;
import org.game.dto.dart.DartContextPropertyDto;
import org.game.entity.DPerform;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CricketPerformByTour implements CricketPerformGame{

    public static final String TOUR = "TOUR";

    @Override
    public String getType() {
        return TOUR;
    }

    @Override
    @Transactional
    public void persistPerformGame(GamePerformDto dto) {
        List<DartContextPropertyDto> performPlayers = dto
            .properties()
            .values()
            .stream()
            .map(p -> mapPlayerPerformPropertiesToContext((Map<String, Object>) p))
            .toList();
        // todo : enregistrer stat, contacter ia ....
        persistDPerformFromContext(String.valueOf(dto.idJeu()), performPlayers);

    }

    @Transactional
    private void persistDPerformFromContext(String idJeu, List<DartContextPropertyDto>  props){
        props.forEach(p -> {
            Log.info(props);
            DPerform dp = DPerform.findByIdGameAndPlayer(idJeu, p.idJoueur());
            Log.info("entity before : " +dp.toString());
            dp.nombreTour = Integer.parseInt(p.numeroTour());
            dp.score += Integer.parseInt(p.score());
            dp.volees.add(p.volee());
            Log.info("entity after : " +dp.toString());
            dp.persistAndFlush();
        });
    }

    private static DartContextPropertyDto mapPlayerPerformPropertiesToContext(Map<String, Object> props){
        return new DartContextPropertyDto(
            extractStringProperty(ID_JOUEUR, props),
            extractStringProperty(PSEUDO, props),
            extractStringProperty(SCORE, props),
            extractStringProperty(NUM_TOUR, props),
            extractStringProperty(DELTA, props),
            extractStringProperty(VOLEE, props));
        }

    // todo : definir une exception spéciale
    private static String extractStringProperty(String key, Map<String, Object> properties){
        return (String) properties.get(key);
    }

    
    
}
