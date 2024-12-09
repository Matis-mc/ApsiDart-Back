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
import org.common.exceptions.FunctionalException;
import org.game.dto.GamePerformDto;
import org.game.dto.dart.DartPerformDto;
import org.game.entity.DGame;
import org.game.entity.DPerform;
import org.stat.service.DStatService;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CricketPerformByTour implements CricketPerformGame{

    private static final String CRIKET = "CRICKET";
    private static final String POSITION_CLASSEMENT = "positionClassement";
    private static final String POSITION_JEU = "positionJeu";
    public static final String TOUR = "TOUR";

    @Inject
    DStatService dStatService;

    @Override
    public String getType() {
        return TOUR;
    }

    @Override
    @Transactional
    public void persistPerformGame(GamePerformDto dto) {
        List<DartPerformDto> performPlayers = mapToDartContextObject(dto);
        // todo : enregistrer stat, contacter ia ....
        persistDPerformFromContext(String.valueOf(dto.idJeu()), performPlayers);

    }

    @Override
    @Transactional
    public void persistEndGame(GamePerformDto dto) {
        checkStatuGame(dto.idJeu().toString());
        List<DartPerformDto> performPlayers = mapToDartContextObject(dto);
        performPlayers.forEach(p -> dStatService.computePlayerStatForThisGame(CRIKET, p));
        // todo : enregistrer stat, contacter ia ....
        persistDPerformFromContext(String.valueOf(dto.idJeu()), performPlayers);
        persistEndGame(String.valueOf(dto.idJeu()));
    }

    @Transactional
    private void persistDPerformFromContext(String idJeu, List<DartPerformDto>  props){
        checkStatuGame(idJeu);
        props.forEach(p -> {
            Log.info(props);
            DPerform dp = DPerform.findByIdGameAndPlayer(idJeu, p.idJoueur())
                .orElseThrow(() -> new FunctionalException("Aucune performance n'a été initialisé sur cette partie"));
            dp.nombreTour = Integer.valueOf(p.numeroTour());
            dp.score += Integer.parseInt(p.score());
            dp.volees.add(p.volee());
            if(Objects.nonNull(p.positionClassement()) && !"".equals(p.positionClassement())){
                dp.positionClassement = Integer.valueOf(p.positionClassement());
            }
            dp.persistAndFlush();
        });
    }

    private static DartPerformDto mapPlayerPerformPropertiesToContext(Map<String, Object> props){
        return new DartPerformDto(
            DtoUtils.extractStringProperty(ID_JOUEUR, props),
            DtoUtils.extractStringProperty(PSEUDO, props),
            DtoUtils.extractStringProperty(SCORE, props),
            DtoUtils.extractStringProperty(NUM_TOUR, props),
            DtoUtils.extractStringProperty(DELTA, props),
            DtoUtils.extractStringProperty(VOLEE, props),
            DtoUtils.extractStringProperty(POSITION_CLASSEMENT, props)
            );
    }

    @Transactional
    private void persistEndGame(String idJeu){
        DGame dGame = DGame.findById(idJeu);
        dGame.statut = STATUT_COMPLETED;
        dGame.persistAndFlush();
    } 
    
    private List<DartPerformDto> mapToDartContextObject(GamePerformDto dto){
        return dto
            .properties()
            .values()
            .stream()
            .map(p -> mapPlayerPerformPropertiesToContext((Map<String, Object>) p))
            .toList();
    }

    private void checkStatuGame(String idJeu){
        DGame dg = DGame.findById(idJeu);
        if(Objects.isNull(dg)){
            throw new FunctionalException("Aucune partie trouvé pour l'id : " + idJeu);
        }
        if(STATUT_COMPLETED.equals(dg.statut)){
            throw new FunctionalException("La partie avec id : " + idJeu + " est déjà terminé");
        }
    }
       
}