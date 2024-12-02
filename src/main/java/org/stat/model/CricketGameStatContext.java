package org.stat.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.common.exceptions.FunctionalException;
import org.stat.dto.ZoneStatDto;

public class CricketGameStatContext {

    Map<String, ZoneTarget> tableauZone = new HashMap<>();

    public CricketGameStatContext(List<Long> idPlayer){
        List<ZoneTarget> zones = List.of(
            new ZoneTarget(idPlayer, "20", 20),
            new ZoneTarget(idPlayer, "19", 19),
            new ZoneTarget(idPlayer, "18", 18),
            new ZoneTarget(idPlayer, "17", 17),
            new ZoneTarget(idPlayer, "16", 16),
            new ZoneTarget(idPlayer, "15", 15),
            new ZoneTarget(idPlayer, "25", 25),
            new ZoneTarget(idPlayer, "0", 0));
        zones.forEach(z -> tableauZone.put(z.getLabel(), z));
    }

    public void playerHitZone(Long idPlayer, Integer nbHit, String label) throws FunctionalException{
        ZoneTarget zone = tableauZone.get(label);
        if(Objects.isNull(zone)){
            throw new FunctionalException("Aucune zone ne correspond au label : " + label);
        }
        zone.playerHitZoneCricket(idPlayer, nbHit);
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
    
}
