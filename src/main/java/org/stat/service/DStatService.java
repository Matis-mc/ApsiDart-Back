package org.stat.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;


import static org.common.Constant.Stat.INITIAL_ELO;
import org.common.exceptions.FunctionalException;
import org.game.dto.dart.DartPerformDto;
import org.game.entity.DPerform;
import org.jboss.logging.Logger;
import org.multielo.MultiEloService;
import org.stat.dto.DartCommonGameStat;
import org.stat.dto.DartGameStat;
import org.stat.dto.ZoneStatDto;
import org.stat.entity.DGlobalPlayerStat;
import org.stat.model.AvgStat;
import org.stat.model.CricketGameContexte;
import org.stat.model.PctStat;
import org.stat.model.SumStat;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DStatService {

    @Inject
    MultiEloService multiEloService;
    
    private static final Logger LOG = Logger.getLogger(DStatService.class);

    // ------------------------------------ PERSISTER STATISTIQUES -------------------------------------------- \\
    public void computeStatFromPerformances(List<DartPerformDto> dartPerformDtos, String type){

        // on récupère le score elo de chaque joueur
        dartPerformDtos.forEach(d -> d.setElo(getEloByPlayer(d.getIdJoueur())));        
        LOG.info("[Success] récupération de tous les scores elo" + dartPerformDtos.toString());
        
        // on récupère tous les nouveauxElo, 
        List<DartPerformDto> dtoUpdate = multiEloService.processNewEloRating(dartPerformDtos, Double.valueOf(dartPerformDtos.size()));
        LOG.info("[Success] calcul des nouveaux scores elo" + dartPerformDtos.toString());

        // pour chaque joueur, on persiste les statistiques (elo et autres)
        dtoUpdate.forEach(d -> computePlayerStatForThisGame(type, d));
        LOG.info("[Success] Statistiques enregistrés pour chaque joueur");

    }

    public void computePlayerStatForThisGame(String type, DartPerformDto ctx){
        boolean playerIsVictorieux = isVictoire(ctx.getPositionDepart());
        double nbVictoire = playerIsVictorieux?1d:0d;
        LOG.info("ctx" + ctx.toString());
        DGlobalPlayerStat stat = new DGlobalPlayerStat(type, 
                    ctx.getElo(),
                    Long.parseLong(ctx.getIdJoueur()), 
                    Timestamp.valueOf(LocalDateTime.now()),
                    new AvgStat(Double.parseDouble(ctx.getPositionDepart()), "AVG_POSITION"),
                    new AvgStat(Double.parseDouble(ctx.getScore()), "AVG_POINTS"),
                    new PctStat(playerIsVictorieux, "PCT_VICTOIRE"),
                    new AvgStat(computeNombreDartCompleted(ctx.getVolee()), "AVG_DART_COMPLETED"),
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

    private Double getEloByPlayer(String idPlayer){
        {
            return DGlobalPlayerStat.getLastStatByIdJoueur(idPlayer)
            .map(stat -> stat.getEloScore())
            .orElse(INITIAL_ELO);}
    }

        // ------------------------------------ STATISTIQUE PAR PARTIE -------------------------------------------- \\

        /**
         * On calcule le statistiques propres à une partie, en simulant son déroulé à partir des volées.
         * @param idGame
         * @return
         * @throws FunctionalException
         */
    public DartGameStat calculateStatForCricketGame(String idGame) throws FunctionalException{

        // récupération des performances des joueurs
        List<DPerform> performances = DPerform.findByIdGame(Long.parseLong(idGame));
            
        if(Objects.isNull(performances) || performances.size() == 0 ){
            throw new FunctionalException("Aucune performance n'est associé à cette partie");
        }
        // on simule la partie, pour déterminer des statisiques sur son déroulement
        CricketGameContexte contexte = DStatUtils.simulateCricketGame(performances);
        
        // on construit les statistiques une fois la partie simulée
        return getDartGameStatFromDartContexte(contexte);
        
    }

    private DartGameStat getDartGameStatFromDartContexte(CricketGameContexte contexte){
        Map<String, ZoneStatDto> zoneStat = contexte.getListZoneStat();
        DartCommonGameStat commonGameStat = new DartCommonGameStat(null, zoneStat);
        return new DartGameStat(commonGameStat, null);
    }

}