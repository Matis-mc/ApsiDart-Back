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
import org.game.dto.GamePerformRetourDto;
import org.game.dto.dart.DartPerformDto;
import org.game.entity.DGame;
import org.game.entity.DPerform;
import org.ia.CommentateurService;
import org.jboss.logging.Logger;
import org.stat.service.DStatService;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CricketPerformByTour{

    private static final String CRIKET = "CRICKET";
    private static final String POSITION_DEPART = "positionDepart";
    private static final String POSITION_JEU = "historiquePosition";
    public static final String TOUR = "TOUR";

    private static final Logger LOG = Logger.getLogger(CricketPerformByTour.class);

    @Inject
    DStatService dStatService;

    @Inject                                            
    CommentateurService commentateurService;


    @Transactional
    public GamePerformRetourDto persistPerformGame(GamePerformDto dto) {

        List<DartPerformDto> performPlayers = mapToListDto(dto);
        persistDPerform(String.valueOf(dto.idJeu()), performPlayers);
        return new GamePerformRetourDto(getCommentaire(performPlayers));
    }

    @Transactional
    public void persistEndGame(GamePerformDto dto) {
        checkStatuGame(dto.idJeu().toString());
        List<DartPerformDto> performPlayers = mapToListDto(dto);
        dStatService.computeStatFromPerformances(performPlayers, CRIKET);
        persistDPerform(String.valueOf(dto.idJeu()), performPlayers);
        persistEndGame(String.valueOf(dto.idJeu()));
    }

    @Transactional
    private void persistDPerform(String idJeu, List<DartPerformDto>  props){
        checkStatuGame(idJeu);
        props.forEach(p -> {
            Log.info(props);
            DPerform dp = DPerform.findByIdGameAndPlayer(idJeu, p.getIdJoueur())
                .orElseThrow(() -> new FunctionalException("Aucune performance n'a été initialisé sur cette partie"));
            if(tourAlreadyRecord(Integer.parseInt(p.getNumeroTour()),dp.nombreTour)){
                // on est sur un update du dernier tour enregistré
                // on remplace dernière la volée : 
                dp.volees.removeLast();
                dp.volees.add(p.getVolee());
                // on remplace la dernière position de jeu :
                dp.historiquePositionJeu.removeLast();
                dp.historiquePositionJeu.add(p.getPositionDepart());

                dp.score = Integer.parseInt(p.getScore());
            } else {
                dp.historiquePositionJeu.add(p.getPositionDepart());
                dp.nombreTour = Integer.valueOf(p.getNumeroTour());
                dp.score = Integer.parseInt(p.getScore());
                dp.volees.add(p.getVolee());
                if(Objects.nonNull(p.getPositionDepart()) && !"".equals(p.getPositionDepart())){
                    dp.positionDepart = p.getPositionDepart();
                }
            }
            dp.persistAndFlush();
        });
    }

    @Transactional
    private void persistEndGame(String idJeu){
        DGame dGame = DGame.findById(idJeu);
        dGame.statut = STATUT_COMPLETED;
        dGame.persistAndFlush();
    } 
    
    private List<DartPerformDto> mapToListDto(GamePerformDto dto){
        return dto
            .performances()
            .stream()
            .map(p -> mapPropertiesToDto((Map<String, Object>) p))
            .toList();
    }

    
    private static DartPerformDto mapPropertiesToDto(Map<String, Object> props){
        return new DartPerformDto(
            DtoUtils.extractStringProperty(ID_JOUEUR, props),
            DtoUtils.extractStringProperty(PSEUDO, props),
            DtoUtils.extractStringProperty(SCORE, props),
            null,
            DtoUtils.extractIntProperty(POSITION_JEU, props),
            DtoUtils.extractStringProperty(NUM_TOUR, props),
            DtoUtils.extractStringProperty(DELTA, props),
            DtoUtils.extractStringProperty(VOLEE, props),
            DtoUtils.extractIntProperty(POSITION_DEPART, props));
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

    private boolean tourAlreadyRecord(Integer numeroTour, Integer lastTourRecord){
        return numeroTour == lastTourRecord;
    }



    // -------------------------- appel IA pour commentaire ----------------------------- \\

    private String getCommentaire(List<DartPerformDto> performPlayers){
        try {
            return commentateurService.commentVolee(constructPromptFromContext(performPlayers));
        } catch (RuntimeException e) {
            LOG.warn("Impossible d'appeler les endpoint OVH : " + e);
            return "";
        }
    }

    private String constructPromptFromContext(List<DartPerformDto> performPlayers){
        String prompt = "Tour " + performPlayers.get(0).getNumeroTour() + ". ";
        for (DartPerformDto p : performPlayers){
            prompt += p.getPseudo() + " a lancé " + describeVolee(p.getVolee()) + "et a " + p.getScore() + "points";  
        }
        LOG.warn(prompt);
        return prompt;
    }

    private String describeVolee(String volee){
        String voleeDescription = "";
        String[] fleches = volee.split("-");
        for(String f : fleches){
            if(f.contains("T")){
                voleeDescription += "triple " + f.substring(1, 3);
            } else if(f.contains("D")){
                voleeDescription += "double " + f.substring(1, 3);
            } else {
                voleeDescription += f;
            }
            voleeDescription += ", ";
        }
        return voleeDescription;
    }
       
}