package org.stat.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.common.Constant.Cricket.NOMBRE_HIT_TO_CLOSE_ZONE;
import org.jboss.logging.Logger;

public class ZoneTarget {

    private static final Logger LOG = Logger.getLogger(ZoneTarget.class);

    public String labelZone;
    public Integer valueZone;
    private Map<Long, Integer> tableau = new HashMap<>();
    private Map<Long, Integer> pointMarques = new HashMap<>();
    private Map<Long, Integer> pointEncaisse = new HashMap<>();
    private Long firstToClose = 0l;
    private Integer nbTotalHits = 0;
    private Integer maxPointEncaisse = 0;
    private Long idPlayerMaxPointEncaisse = 0l;
    private Integer maxPointMarque = 0;
    private Long idPlayerMaxPointMarque = 0l;


    public ZoneTarget(List<Long> idPlayers, String label, Integer value){
        this.labelZone = label;
        this.valueZone = value;
        idPlayers.forEach(i -> {
            tableau.put(i, 0);
            pointMarques.put(i, 0);
            pointEncaisse.put(i, 0);
        });
    }

    public Integer getValue(){
        return valueZone;
    }

    public String getLabel(){
        return labelZone;
    }

    public Long getFirstToClose(){
        return firstToClose;
    }

    public void setFirstToClose(Long idPlayer){
        firstToClose = idPlayer;
    }

    public Integer getNbTotalHit(){
        return nbTotalHits;
    }

    public void incrHitTotalByOne(){
        nbTotalHits += 1;
    }

    public Long getPlayerScoredMorePoint(){
        return idPlayerMaxPointMarque;
    }

    public void setPlayerScoredMorePoint(Long idPlayer){
        idPlayerMaxPointMarque = idPlayer;;
    }

    public Integer getMaxAmountPointScored(){
        return maxPointMarque;
    }

    public void setMaxAmountPointscored(Integer value){
        maxPointMarque = value;
    }

    public Long getPlayerTakeMorePoint(){
        return idPlayerMaxPointEncaisse;
    }

    public Integer getMaxAmountPointTaken(){
        return maxPointEncaisse;
    }

    public boolean hasPlayerClosedZone(Long idPlayer){
        Integer nbDartAlreadyIn = tableau.getOrDefault(idPlayer, 0);
        return nbDartAlreadyIn >= NOMBRE_HIT_TO_CLOSE_ZONE;
    }

    public Integer getPointMarquesPlayer(Long idPlayer){
        return pointMarques.getOrDefault(idPlayer, 0);
    }

    public Integer getPointEncaissesPlayer(Long idPlayer){
        return pointEncaisse.getOrDefault(idPlayer, 0);
    }

    public Integer nbDartAlreadyIn(Long idPlayer){
        return tableau.getOrDefault(idPlayer, 0);
    }

    public void ajoutDartForPlayerInZoneTableau(Long idPlayer, Integer nbDartNow){
        tableau.put(idPlayer, nbDartNow);
    }

    public void playerHitZoneCricket(Long idPlayer, Integer nbHit){
        Integer nbDartAlreadyIn = tableau.getOrDefault(idPlayer, 0);
        Integer nbDartNow = nbDartAlreadyIn + nbHit;
        if(nbDartNow >= NOMBRE_HIT_TO_CLOSE_ZONE){
            if(firstToClose == 0l){
                firstToClose = idPlayer;
            }
            Integer pointScored = (nbDartNow - NOMBRE_HIT_TO_CLOSE_ZONE) * valueZone;
            if(pointScored > 0 ){
                if(pointScored > maxPointMarque){
                    maxPointMarque = pointScored;
                    idPlayerMaxPointMarque = idPlayer;
                }
                ajoutPointMarque(idPlayer, pointScored);  
            }
        }
        tableau.put(idPlayer, nbDartNow);
        nbTotalHits += nbHit;
    }

    private void ajoutPointMarque(Long idPlayer, Integer ptMarque){
        Integer pointsAlreadyScored = getPointMarquesPlayer(idPlayer);
        pointMarques.put(idPlayer, pointsAlreadyScored + ptMarque);
        // on recupère tous les joueurs qui n'ont pas ferme la zone
        tableau.keySet()
            .stream()
            .filter(id -> !idPlayer.equals(id))
            .filter(id -> !hasPlayerClosedZone(id))
            .toList()
            .forEach(p -> ajoutPointEncaisse(p, ptMarque));
    }

    private void ajoutPointEncaisse(Long idPlayer, Integer ptEncaisses){
        Integer pointsAlreadyEncaisse = getPointEncaissesPlayer(idPlayer);
        Integer totalPointEncaisse = pointsAlreadyEncaisse + ptEncaisses;
        checkMaxPointEncaisseStat(idPlayer, totalPointEncaisse);
        pointEncaisse.put(idPlayer, totalPointEncaisse);
    }

    private void checkMaxPointEncaisseStat(Long idPlayer, Integer value){
        if(value > maxPointEncaisse){
            maxPointEncaisse = value;
            idPlayerMaxPointEncaisse = idPlayer;
        }
    }

    @Override
    public String toString() {
        return "[zone=" + labelZone + ", " + tableau + ", nbHit=" + nbTotalHits + ", firstToClose=" + firstToClose + ", ptEncaisse=" + pointEncaisse + ", pyMarque=" + pointMarques +"] \n";
    }

    

}
