package org.stat.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.common.Constant.Game.DOUBLE_PREFIX;
import static org.common.Constant.Game.TRIBLE_PREFIX;
import org.common.exceptions.FunctionalException;
import org.game.dto.dart.DartPerformDto;
import org.game.entity.DPerform;
import org.jboss.logging.Logger;
import org.stat.dto.DartCommonGameStat;
import org.stat.dto.DartGameStat;
import org.stat.dto.ZoneStatDto;
import org.stat.entity.DGlobalPlayerStat;
import org.stat.model.AvgStat;
import org.stat.model.CricketGameStatContext;
import org.stat.model.PctStat;
import org.stat.model.SumStat;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DStatService {
    
    private static final Logger LOG = Logger.getLogger(DStatService.class);

    // ------------------------------------ PERSISTER STATISTIQUE -------------------------------------------- \\

    public void computePlayerStatForThisGame(String type, DartPerformDto ctx){
        boolean playerIsVictorieux = isVictoire(ctx.positionClassement());
        double nbVictoire = playerIsVictorieux?1d:0d;
        LOG.info("ctx" + ctx.toString());
        DGlobalPlayerStat stat = new DGlobalPlayerStat(type, 
                    Long.parseLong(ctx.idJoueur()), 
                    Timestamp.valueOf(LocalDateTime.now()),
                    new AvgStat(Double.parseDouble(ctx.positionClassement()), "AVG_POSITION"),
                    new AvgStat(Double.parseDouble(ctx.score()), "AVG_POINTS"),
                    new PctStat(playerIsVictorieux, "PCT_VICTOIRE"),
                    new AvgStat(computeNombreDartCompleted(ctx.volee()), "AVG_DART_COMPLETED"),
                    new SumStat(1d, "SUM_NB_GAME"),
                    new SumStat(nbVictoire, "SUM_NB_VICTOIRE"));
        stat.persistStat();
        stat.persistAndFlush();      
    }

    private boolean isVictoire(String position){
        return Integer.parseInt(position) == 1;
    }

    private double computeNombreDartCompleted(String volees){
        return 0d;
    }

        // ------------------------------------ STATISTIQUE PAR PARTIE -------------------------------------------- \\

    public DartGameStat calculateStatForCricketGame(String idGame) throws FunctionalException{

        // récupération des performances des joueurs
        List<DPerform> performances = DPerform.findByIdGame(Long.parseLong(idGame));
        if(Objects.isNull(performances) || performances.size() == 0 ){
            throw new FunctionalException("Aucune performance n'est associé à cette partie");
        }

        // on simule la partie, pour déterminer des statisiques de déroulement
        // on trie la partie par ordre de jeu
        performances.sort(Comparator.comparing(DPerform::getPositionJeu));

        // on initialise le contexte de la partie
        CricketGameStatContext contexte = new CricketGameStatContext(performances.stream().map(d -> d.getDartPlayer().id).toList());
        LOG.warn("context just after initialisation : " + contexte.toString());
        // on simule la partie, volee par volee
        Integer nbTours = performances.get(0).getNombreTour();
        LOG.warn("nbTours : " + nbTours);
        for(int nbTour = 0; nbTour < nbTours; nbTour++){
            final int tempIndex = nbTour;
            LOG.warn("nbTour actuel : " + tempIndex);
            performances.forEach(
                p -> simulateVolee(p, tempIndex, contexte)
            );
        }
        // on construit les statistiques une fois la partie simulée
        return getDartGameStatFromDartContexte(contexte);

        
    }

    private void simulateVolee(DPerform p, int nbTour, CricketGameStatContext contexte){
        try{
            String volee = p.getVolees().get(nbTour);
            Long idPlayer = p.getDartPlayer().id;
            playVolee(volee, idPlayer, contexte);
        } catch (FunctionalException e) {
            LOG.error(e.getMessage());
        } catch (IndexOutOfBoundsException e){
            // ignore => ça veut dire que la partie à été terminé par des joueurs qui jouaient avant dans le tour
            LOG.error(e.getMessage());
        }
    }

    private  void playVolee(String volee, Long idPlayer, CricketGameStatContext contexte) throws FunctionalException{
        LOG.warn("Simulation de la volée : " + volee);
        String[] darts = volee.split("-");
        for (String dart : darts){
            if (dart.contains(DOUBLE_PREFIX)){
                String label = dart.substring(1);
                contexte.playerHitZone(idPlayer, 2, label);
            }
            else if (dart.contains(TRIBLE_PREFIX)){
                String label = dart.substring(1);
                contexte.playerHitZone(idPlayer, 3, label);
            } else {
                contexte.playerHitZone(idPlayer, 1, dart);
            }
        }
        LOG.warn("Context après simulation de la volée : " + contexte.toString());
    }

    private DartGameStat getDartGameStatFromDartContexte(CricketGameStatContext contexte){
        
        Map<String, ZoneStatDto> zoneStat = contexte.getListZoneStat();
        DartCommonGameStat commonGameStat = new DartCommonGameStat(null, zoneStat);
        return new DartGameStat(commonGameStat, null);
    }



}
