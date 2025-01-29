package org.stat.dto;

public record ZoneStatDto(
    Long idPlayerFirstToClose,
    Long idPlayerTakeMorePoint,
    Integer maxPointTakenByPlayer,
    Long idPlayerScoreMorePoint,
    Integer maxPointScoredByPlayer
) {
    
}
