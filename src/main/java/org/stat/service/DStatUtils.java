package org.stat.service;

import java.util.Comparator;
import java.util.List;

import org.common.exceptions.FunctionalException;
import org.game.entity.DPerform;
import org.jboss.logging.Logger;
import org.stat.model.CricketGameContexte;

import static org.common.Constant.Game.DOUBLE_PREFIX;
import static org.common.Constant.Game.TRIPLE_PREFIX;


public class DStatUtils {

    private static final Logger LOG = Logger.getLogger(DStatUtils.class);

    public static CricketGameContexte simulateCricketGame(List<DPerform> performances){
        // on trie la partie par ordre de jeu
        performances.sort(Comparator.comparing(DPerform::getPositionDepart));

        // on récupère la liste des id de joueurs 
        List<Long> idPlayers =  performances.stream().map(d -> d.getDartPlayer().id).toList();

        // on initialise le contexte de la partie
        CricketGameContexte contexte = new CricketGameContexte(idPlayers);
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
        return contexte;
    }

    private static void simulateVolee(DPerform p, int nbTour, CricketGameContexte contexte){
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

    private static void playVolee(String volee, Long idPlayer, CricketGameContexte contexte) throws FunctionalException{
        LOG.warn("Simulation de la volée : " + volee);
        String[] darts = volee.split("-");
        for (String dart : darts){
            if (dart.contains(DOUBLE_PREFIX)){
                contexte.playerHitZone(idPlayer, 2, dart.substring(1));
            }
            else if (dart.contains(TRIPLE_PREFIX)){
                contexte.playerHitZone(idPlayer, 3, dart.substring(1));
            } else {
                contexte.playerHitZone(idPlayer, 1, dart);
            }
        }
        LOG.warn("Context après simulation de la volée : " + contexte.toString());
    }
}
