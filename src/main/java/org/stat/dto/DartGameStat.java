package org.stat.dto;

import java.util.List;

import org.stat.entity.DGlobalPlayerStat;

public record DartGameStat (
    DartCommonGameStat gameStats,
    List<DGlobalPlayerStat> indivStats){
}
