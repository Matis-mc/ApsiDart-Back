package org.game.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.player.entity.Player;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Parameters;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table( name = "dartPerform")
public class DPerform extends PanacheEntity{

    @ManyToOne
    public Player dartPlayer;

    @ManyToOne
    public DGame dartGame;

    public Integer positionJeu;

    public Integer positionClassement;

    public Integer score;

    public Integer nombreTour;

    public List<String> volees;

    public static Optional<DPerform> findByIdGameAndPlayer(String idGame, String idPlayer){
        return find("SELECT dp FROM DPerform dp WHERE dp.dartPlayer.id = :idPlayer and dp.dartGame.id = :idGame", Parameters.with("idGame", idGame).and("idPlayer", idPlayer)).firstResultOptional();
    }

    public static List<DPerform> findByIdGame(Long idGame){
        return find("SELECT dp FROM DPerform dp WHERE  dp.dartGame.id = :idGame", Parameters.with("idGame", idGame)).list();
    }

    public static List<DPerform> findByIdPlayer(Long idPlayer){
        return find("SELECT dp FROM DPerform dp WHERE dp.dartPlayer.id = :idPlayer", Parameters.with("idPlayer", idPlayer)).list();
    }

    public static DPerform createDPerform(DGame dGame, Player participant, int positionJeu){
        DPerform dPerform = new DPerform();
        dPerform.dartGame = dGame;
        dPerform.dartPlayer = participant;
        dPerform.positionJeu = positionJeu;
        dPerform.positionClassement = 0;
        dPerform.score = 0;
        dPerform.nombreTour = 0;
        dPerform.volees = new ArrayList<>();
        dPerform.persist();
        return dPerform;
    }

    


    @Override
    public String toString() {
        return "DPerform [dartPlayer=" + dartPlayer + ", dartGame=" + dartGame + ", positionJeu=" + positionJeu
                + ", positionClassement=" + positionClassement + ", score=" + score + ", nombreTour=" + nombreTour
                + ", volees=" + volees + "]";
    }

    public Player getDartPlayer() {
        return dartPlayer;
    }

    public Integer getPositionJeu() {
        return positionJeu;
    }

    public Integer getPositionClassement() {
        return positionClassement;
    }

    public Integer getScore() {
        return score;
    }

    public Integer getNombreTour() {
        return nombreTour;
    }

    public List<String> getVolees() {
        return volees;
    }



    


}
