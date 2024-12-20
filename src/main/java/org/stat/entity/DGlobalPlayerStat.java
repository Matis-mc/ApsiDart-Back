package org.stat.entity;

import java.sql.Timestamp;
import java.util.List;
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
@Table(name = "dartGlobalPlayerStats")
public class DGlobalPlayerStat extends PanacheEntity{
    
    public String type;
    public Long idPlayer;
    public Timestamp date;

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

    public static List<DGlobalPlayerStat> getAllStatByIdJoueur(String idJoueur){
       List<DGlobalPlayerStat> listDStat = DGlobalPlayerStat.find("SELECT s FROM DGlobalPlayerStat s WHERE s.idPlayer = :idPlayer", Parameters.with("idPlayer", idJoueur)).list();
       return listDStat;
    }

    public static Optional<DGlobalPlayerStat> getLastStatByIdJoueur(String idJoueur){
        return DGlobalPlayerStat
            .find("SELECT s FROM DGlobalPlayerStat s WHERE s.idPlayer = :idPlayer ORDER BY s.date DSC", Parameters.with("idPlayer", idJoueur))
            .firstResultOptional();
    }

    public DGlobalPlayerStat(){
        
    }

    public DGlobalPlayerStat(String type, Long idPlayer, Timestamp date, AvgStat avgPosition, AvgStat avgPoints, PctStat pctVictoire,
            AvgStat avgNbDartCompleted, SumStat nbGame, SumStat nbVictoire) {
        this.type = type;
        this.idPlayer = idPlayer;
        this.date = date;
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
