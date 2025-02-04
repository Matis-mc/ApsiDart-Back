package org.stat.dto;

public record DartIndivGameStat (
    String idGame,
    String idPlayer,
    String pseudo,
    int nbDartThrow,
    double markByTour,
    double precision){

}
