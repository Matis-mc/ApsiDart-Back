package org.game.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.Map;

import org.player.entity.Player;


@Entity
@Table(name = "dartGame")
public class DartGameEntity extends PanacheEntity {

    private String type;

    private LocalDate date;
    
}