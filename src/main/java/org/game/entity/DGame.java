package org.game.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.player.entity.Player;


@Entity
@Table(name = "dartGame")
public class DGame extends PanacheEntity {

    public String type;

    public String statut;

    public LocalDate date;

    @OneToMany
    public List<DPerform> dPerform;


    
}