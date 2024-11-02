package org.game.entity;

import org.player.entity.Player;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "dartPlayer")
public class DartPlayerEntity extends PanacheEntity {

    @OneToOne
    private Player player;

    private double elo;
    
}
