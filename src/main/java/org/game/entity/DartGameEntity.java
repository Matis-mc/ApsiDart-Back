package org.game.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.util.Map;

import org.player.entity.Player;


@Entity
public class DartGameEntity extends PanacheEntity {

    public Map<Integer, Player> classements;
    
}