package org.stat.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.common.exceptions.FunctionalException;
import org.stat.dto.ZoneStatDto;

/**
 * Cette classe doit être le point central de la régénération de partie de cricket dans le but de reclaculer toute les statistiques nécessaires.
 */
public class CricketGameContexte {

    Map<String, ZoneTarget> tableauZone = new HashMap<>();
    Map<Integer, List<ScorePlayer>> classementByTour = new HashMap<>();

    public CricketGameContexte(List<Long> idPlayers){
        // on initialise les zones pour du cricket
        List<ZoneTarget> zones = List.of(
            new ZoneTarget(idPlayers, "20", 20),
            new ZoneTarget(idPlayers, "19", 19),
            new ZoneTarget(idPlayers, "18", 18),
            new ZoneTarget(idPlayers, "17", 17),
            new ZoneTarget(idPlayers, "16", 16),
            new ZoneTarget(idPlayers, "15", 15),
            new ZoneTarget(idPlayers, "25", 25),
            new ZoneTarget(idPlayers, "0", 0));
        zones.forEach(z -> tableauZone.put(z.getLabel(), z));
        // on initialise le classement pour le tour 0 :
        classementByTour.put(0, idPlayers.stream().map(p -> new ScorePlayer(p, 0)).toList());
    }

    public void playerHitZone(Long idPlayer, Integer nbHit, String label) throws FunctionalException{
        ZoneTarget zone = tableauZone.get(label);
        if(Objects.isNull(zone)){
            throw new FunctionalException("Aucune zone ne correspond au label : " + label);
        }
        zone
        .playerHitZoneCricket(idPlayer, nbHit);
    }

    public Map<String, ZoneStatDto> getListZoneStat(){
        Map<String, ZoneStatDto> stats = new HashMap<>();
        tableauZone.values().forEach(v -> stats.put(v.getLabel(), getStatForZoneTarget(v)));
        return stats;
    }

    private ZoneStatDto getStatForZoneTarget(ZoneTarget zt){
        return new ZoneStatDto(
            zt.getFirstToClose(),
            zt.getPlayerTakeMorePoint(),
             zt.getMaxAmountPointTaken(),
             zt.getPlayerScoredMorePoint(), 
             zt.getMaxAmountPointScored());
    }


    @Override
    public String toString() {
        return "context : [" + tableauZone + "]";
    }
    
}
