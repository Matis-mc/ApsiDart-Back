package org.game.entity;

import java.util.List;

import org.player.entity.Player;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
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

    public Integer position;

    public Integer score;

    public List<String> volees;


}
