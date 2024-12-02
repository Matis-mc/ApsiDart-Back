package org.stat.dto;

import java.util.List;

public record DartGameStat (
    DartCommonGameStat commonStat,
    List<DartIndivGameStat> indivStats){
}
