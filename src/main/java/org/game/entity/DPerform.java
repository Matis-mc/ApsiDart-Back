package org.game.entity;

import java.util.List;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.player.entity.Player;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Parameters;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table( name = "dartPerform")
@NamedQueries({
    @NamedQuery(name = "DPerform.findByIdGameAndIdPlayer", 
    query = "from DPerform dp where dp.dartPlayer.id = :idPlayer and dp.dartGame.id = :idGame")
})
public class DPerform extends PanacheEntity{

    @ManyToOne
    public Player dartPlayer;

    @ManyToOne
    public DGame dartGame;

    public Integer position;

    public Integer score;

    public Integer nombreTour;

    public List<String> volees;

    public static DPerform findByIdGameAndPlayer(String idGame, String idPlayer){
        return find("SELECT dp FROM DPerform dp WHERE dp.dartPlayer.id = :idPlayer and dp.dartGame.id = :idGame", Parameters.with("idGame", idGame).and("idPlayer", idPlayer)).firstResult();
    }

    @Override
    public String toString() {
        return "DPerform [dartPlayer=" + dartPlayer + ", dartGame=" + dartGame + ", position=" + position + ", score="
                + score + ", nombreTour=" + nombreTour + ", volees=" + volees + "]";
    }


}
