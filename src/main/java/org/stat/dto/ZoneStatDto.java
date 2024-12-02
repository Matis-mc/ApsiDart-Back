package org.stat.dto;

public record ZoneStatDto(
    Long idPlayerFirstToclose,
    Long idPlayerTakeMorePoint,
    Integer morepointTakenByPayer,
    Long idPlayerScoreMorePoint,
    Integer morepointScoredByPayer
) {
    
}
