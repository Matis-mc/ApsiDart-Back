package org.stat.dto;

public record DartIndivGameStat (
    String idGame,
    String idPlayer,
    String pseudo,
    int nbDartThrow,
    double avgScoreByThrow,
    double pctTriple,
    double pctDouble,
    double pctSimple,
    double pct0){

}
