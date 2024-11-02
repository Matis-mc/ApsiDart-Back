package org.game.entity;

import java.util.List;

import org.hibernate.annotations.OnDelete;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table( name = "playerPlayDartGame")
public class DPlayerPlayDGameEntity {

    @OneToOne
    private DartPlayerEntity dartPlayer;

    @ManyToOne
    private DartGameEntity dartGame;

    private Integer position;

    private Integer score;

    private List<String> volees;


}
