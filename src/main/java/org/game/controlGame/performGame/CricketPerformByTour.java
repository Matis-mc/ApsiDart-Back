package org.game.controlGame.performGame;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.common.Constant.Game.STATUT_COMPLETED;
import static org.common.DartConstant.Context.DELTA;
import static org.common.DartConstant.Context.ID_JOUEUR;
import static org.common.DartConstant.Context.NUM_TOUR;
import static org.common.DartConstant.Context.PSEUDO;
import static org.common.DartConstant.Context.SCORE;
import static org.common.DartConstant.Context.VOLEE;
import org.common.DtoUtils;
import org.game.dto.GamePerformDto;
import org.game.dto.dart.DartContextPropertyDto;
import org.game.entity.DGame;
import org.game.entity.DPerform;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CricketPerformByTour implements CricketPerformGame{

    private static final String POSITION = "position";
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

    @Override
    @Transactional
    public void persistEndGame(GamePerformDto dto) {
        List<DartContextPropertyDto> performPlayers = dto
            .properties()
            .values()
            .stream()
            .map(p -> mapPlayerPerformPropertiesToContext((Map<String, Object>) p))
            .toList();
        // todo : enregistrer stat, contacter ia ....
        persistDPerformFromContext(String.valueOf(dto.idJeu()), performPlayers);
        persistEndGame(String.valueOf(dto.idJeu()));
    }

    @Transactional
    private void persistDPerformFromContext(String idJeu, List<DartContextPropertyDto>  props){
        props.forEach(p -> {
            Log.info(props);
            DPerform dp = DPerform.findByIdGameAndPlayer(idJeu, p.idJoueur());
            dp.nombreTour = Integer.parseInt(p.numeroTour());
            dp.score += Integer.parseInt(p.score());
            dp.volees.add(p.volee());
            if(Objects.nonNull(p.position()) && p.position() != ""){
                dp.position = Integer.parseInt(p.position());
            }
            dp.persistAndFlush();
        });
    }

    private static DartContextPropertyDto mapPlayerPerformPropertiesToContext(Map<String, Object> props){
        return new DartContextPropertyDto(
            DtoUtils.extractStringProperty(ID_JOUEUR, props),
            DtoUtils.extractStringProperty(PSEUDO, props),
            DtoUtils.extractStringProperty(SCORE, props),
            DtoUtils.extractStringProperty(NUM_TOUR, props),
            DtoUtils.extractStringProperty(DELTA, props),
            DtoUtils.extractStringProperty(VOLEE, props),
            DtoUtils.extractStringProperty(POSITION, props));
    }

    @Transactional
    private void persistEndGame(String idJeu){
        DGame dGame = DGame.findById(idJeu);
        dGame.statut = STATUT_COMPLETED;
        dGame.persistAndFlush();
    }   
    

   
}