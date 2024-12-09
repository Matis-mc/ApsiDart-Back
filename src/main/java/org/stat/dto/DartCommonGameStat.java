package org.stat.dto;

import java.util.List;
import java.util.Map;

public record DartCommonGameStat (
    List<Long> classementIdPlayers,
    Map<String, ZoneStatDto> zonestats
){
    
}
