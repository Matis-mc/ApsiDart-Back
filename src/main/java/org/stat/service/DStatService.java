package org.stat.service;

import org.game.dto.dart.DartContextPropertyDto;
import org.stat.entity.DStat;
import org.stat.model.AvgStat;
import org.stat.model.PctStat;
import org.stat.model.SumStat;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DStatService {
    
    
    public void computeDStatFromDartContextProperty(String type, DartContextPropertyDto ctx){
        boolean playerIsVictorieux = isVictoire(ctx.position());
        double nbVictoire = playerIsVictorieux?1d:0d;
        DStat stat = DStat.getStatByIdJoueur(ctx.idJoueur())
            .map((DStat s) -> {
                s.avgPoints.computeStat(Double.parseDouble(ctx.score()));
                s.avgPosition.computeStat(Double.parseDouble(ctx.position()));
                s.avgNbDartCompleted.computeStat(computeNombreDartCompleted(ctx.volee()));
                s.nbGame.computeStat(1d);
                s.nbVictoire.computeStat(nbVictoire);
                s.pctVictoire.computeStat(nbVictoire);
                return s;

            } )
            .orElse(
                new DStat(type, 
                    Long.getLong(ctx.idJoueur()), 
                    new AvgStat(Double.parseDouble(ctx.position()), "AVG_POSITION"),
                    new AvgStat(Double.parseDouble(ctx.score()), "AVG_POINTS"),
                    new PctStat(playerIsVictorieux, "PCT_VICTOIRE"),
                    new AvgStat(computeNombreDartCompleted(ctx.volee()), "AVG_DART_COMPLETED"),
                    new SumStat(1d, "SUM_NB_GAME"),
                    new SumStat(nbVictoire, "SUM_NB_VICTOIRE")));
        stat.persistStat();
        stat.persistAndFlush();
            
    }

    private boolean isVictoire(String position){
        return Integer.parseInt(position) == 1;
    }

    private double computeNombreDartCompleted(String volees){
        return 0d;
    }
}
