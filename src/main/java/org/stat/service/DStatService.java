package org.stat.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.common.Constant.Game.DOUBLE_PREFIX;
import static org.common.Constant.Game.TRIBLE_PREFIX;
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
import org.stat.model.CricketGameStatContext;
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
        boolean playerIsVictorieux = isVictoire(ctx.getPositionClassement());
        double nbVictoire = playerIsVictorieux?1d:0d;
        LOG.info("ctx" + ctx.toString());
        DGlobalPlayerStat stat = new DGlobalPlayerStat(type, 
                    ctx.getElo(),
                    Long.parseLong(ctx.getIdJoueur()), 
                    Timestamp.valueOf(LocalDateTime.now()),
                    new AvgStat(Double.parseDouble(ctx.getPositionClassement()), "AVG_POSITION"),
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
