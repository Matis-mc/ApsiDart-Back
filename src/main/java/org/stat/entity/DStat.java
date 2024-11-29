package org.stat.entity;

import java.util.Optional;

import org.stat.model.AvgStat;
import org.stat.model.PctStat;
import org.stat.model.SumStat;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Parameters;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "dartStats")
public class DStat extends PanacheEntity{
    
    public String type;
    public Long idPlayer;

    @OneToOne
    public AvgStat avgPosition;

    @OneToOne
    public AvgStat avgPoints;

    @OneToOne
    public PctStat pctVictoire;

    @OneToOne
    public AvgStat avgNbDartCompleted;

    @OneToOne
    public SumStat nbGame;

    @OneToOne
    public SumStat nbVictoire;

    public static Optional<DStat> getStatByIdJoueur(String idJoueur){
       Optional<DStat> optDStat = DStat.find("SELECT s FROM DStat s WHERE s.idPlayer = :idPlayer", Parameters.with("idPlayer", idJoueur)).firstResultOptional();
       return optDStat;
    }

    public DStat(){
        
    }

    public DStat(String type, Long idPlayer, AvgStat avgPosition, AvgStat avgPoints, PctStat pctVictoire,
            AvgStat avgNbDartCompleted, SumStat nbGame, SumStat nbVictoire) {
        this.type = type;
        this.idPlayer = idPlayer;
        this.avgPosition = avgPosition;
        this.avgPoints = avgPoints;
        this.pctVictoire = pctVictoire;
        this.avgNbDartCompleted = avgNbDartCompleted;
        this.nbGame = nbGame;
        this.nbVictoire = nbVictoire;
    }

    public void persistStat(){
        this.avgPosition.persist();
        this.avgPoints.persist();
        this.pctVictoire.persist();
        this.avgNbDartCompleted.persist();
        this.nbGame.persist();
        this.nbVictoire.persist();
    }

    

}
